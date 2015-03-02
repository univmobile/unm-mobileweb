package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Nullable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.univmobile.backend.client.AppToken;
import fr.univmobile.backend.client.ClientException;
import fr.univmobile.backend.client.SSOConfiguration;
import fr.univmobile.backend.client.SessionClient;
import fr.univmobile.backend.client.SessionClient.LoginConversation;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.User;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "login/shibboleth", "login/shibboleth/" })
public class LoginShibbolethController extends AsbtractMobileWebJspController {

	public LoginShibbolethController(final String apiKey,
			final SessionClient sessionClient, final String jsonUrl) {

		this.apiKey = checkNotNull(apiKey, "apiKey");
		this.sessionClient = checkNotNull(sessionClient, "sessionClient");
		this.jsonUrl = jsonUrl;
	}

	private final String apiKey;
	private final SessionClient sessionClient;
	private final String jsonUrl;

	private static final Log log = LogFactory
			.getLog(LoginShibbolethController.class);

	/**
	 * To login to the application, the 
	 */
	@Override
	@Nullable
	public View action() throws IOException, ClientException {

		if (getUniversity() == null || hasSessionAttribute("currentUser")) {
			sendRedirect(getBaseURL() + "/");
			return null;
		} else {

			final PreparedLogin prepared = getHttpInputs(PreparedLogin.class);

			if (prepared.isHttpValid()) {

				final String loginToken = getSessionAttribute("loginToken",
						String.class);
				final String key = getSessionAttribute("key", String.class);

				if (!prepared.loginToken().equals(loginToken)) {

					log.error("Expected loginToken (memory): " + loginToken
							+ ", but was: " + prepared.loginToken());

					return sendError400();
				}

				final AppToken appToken = sessionClient.retrieve(apiKey,
						loginToken, key);

				if (appToken == null) {

					throw new IllegalStateException("Invalid loginToken");
				}

				// Valid login
				log.info("Login successfull for user of id : " + appToken.getUser().getUid());
				
				User currentUser = restTemplate().getForObject(jsonUrl + "/users/ " + appToken.getUser().getUid(), User.class);
				if (currentUser != null) {
					setSessionAttribute("currentUser", currentUser);
				}

				setSessionAttribute("authenticationToken", appToken.getId());

				setSessionAttribute("currentUser", currentUser);

				if (hasSessionAttribute("currentUser")) {		
					
					//redirect to home or previous page
					sendRedirect(getBaseURL()+"/");
					return null;
				}
			}

			final University university = getUniversity(); 
					
			//setAttribute("selectedUniversityId", university.getId());
			//setAttribute("selectedUniversityLabel", university.getTitle());

			final String shibbolethIdentityProvider = university
					.getMobileShibbolethUrl();

			if (shibbolethIdentityProvider == null || shibbolethIdentityProvider.isEmpty()) {

				log.error("No Shibboleth Identity Provider for univ: " + university.getTitle() + "(id:" + university.getId() + "). Redirect to standard login.");

				return sendRedirect(getBaseURL()+"/login");

			}

			// The "?loginToken=xx" HTTP parameter will be added by the
			// unm-backend webapp.
			final String callbackURL = getBaseURL() + "/login/shibboleth/";

			final LoginConversation conversation = sessionClient
					.prepare(apiKey);

			final String loginToken = conversation.getLoginToken();
			final String key = conversation.getKey();

			if (log.isInfoEnabled()) {
				log.info("Shibboleth loginToken: " + loginToken);
				log.info("Shibboleth key: " + key);
			}

			setSessionAttribute("loginToken", loginToken);
			setSessionAttribute("key", key);

			final SSOConfiguration sso = sessionClient.getSSOConfiguration();

			final String targetURL =
			// "https://univmobile-dev.univ-paris1.fr/testSP/" //
			// + "?loginToken=" + loginToken //
			// + "&callback=" + encodeURL(callbackURL);
			sso.getTargetURL().replace("${loginToken}", loginToken)
					.replace("${callback.url}", encodeURL(callbackURL));

			final String ssoURL = //
			// "https://univmobile-dev.univ-paris1.fr/testSP/shibboleth/" //
			// + "paris1" + "?service=" + encodeURL(callbackURL);
			// "https://univmobile-dev.univ-paris1.fr/Shibboleth.sso/Login" //
			// + "?target=" + encodeURL(target) //
			// + "&entityID=https://idp-test.univ-paris1.fr";
			// + "&entityID=" + encodeURL(shibbolethIdentityProvider);
			sso.getURL()
					.replace("${target.url}", encodeURL(targetURL))
					.replace("${shibboleth.entityProvider}",
							encodeURL(shibbolethIdentityProvider));

			// service=https://idp-test.univ-paris1.fr/idp/Authn/RemoteUser
			if (log.isDebugEnabled()) {
				log.debug("Shibboleth login of university redirect to login URL : " + ssoURL);
			}

			return sendRedirect(ssoURL);
		}
	}

	static String encodeURL(final String url) {

		try {

			return URLEncoder.encode(url, UTF_8);

		} catch (final UnsupportedEncodingException e) {

			throw new RuntimeException(e);
		}
	}

	@HttpMethods("GET")
	interface PreparedLogin extends HttpInputs {

		/**
		 * e.g. loginToken=79894e4d-a391-4381-b752-5ab88309c003-vnQ
		 */
		@HttpRequired
		@HttpParameter
		String loginToken();
	}
}
