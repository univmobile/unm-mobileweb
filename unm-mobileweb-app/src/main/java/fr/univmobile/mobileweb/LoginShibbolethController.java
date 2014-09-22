package fr.univmobile.mobileweb;

import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Nullable;

import fr.univmobile.web.commons.AbstractJspController;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "login/shibboleth", "login/shibboleth/" })
public class LoginShibbolethController extends AbstractJspController {

	@Override
	@Nullable
	public View action() throws IOException {

		final String callbackURL = getBaseURL() + "login/shibboleth/";

		final String ssoURL = //
		"https://univmobile-dev.univ-paris1.fr/testSP/shibboleth/" //
				+ "paris1" + "?service=" + encodeURL(callbackURL);

		// service=https://idp-test.univ-paris1.fr/idp/Authn/RemoteUser

		return sendRedirect(ssoURL);
	}

	static String encodeURL(final String url) {

		try {

			return URLEncoder.encode(url, UTF_8);

		} catch (final UnsupportedEncodingException e) {

			throw new RuntimeException(e);
		}
	}
}
