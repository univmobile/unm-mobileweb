package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import fr.univmobile.backend.client.AppToken;
import fr.univmobile.backend.client.ClientException;
import fr.univmobile.backend.client.SessionClient;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.User;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "login" })
public class LoginController extends AsbtractMobileWebJspController {
	
	private static final Log log = LogFactory.getLog(LoginController.class);
	
	protected final String jsonUrl;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public LoginController(final String apiKey,
			final SessionClient sessionClient, String jsonUrl) {

		this.apiKey = checkNotNull(apiKey, "apiKey");
		this.sessionClient = checkNotNull(sessionClient, "sessionClient");
		this.jsonUrl = jsonUrl;
	}

	private final String apiKey;
	private final SessionClient sessionClient;


	/********************************************************
	 * This method is called automatically on /login view
	 ********************************************************/
	@Override
	public View action() throws IOException, ClientException {
		
		RestTemplate template = restTemplate();
				
		if (!hasSessionAttribute("univ")) {

			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {	
			
			String previousPageAbsolutePath = null;
			final Login login = getHttpInputs(Login.class);
			if (login.isHttpValid()) {
				
				//check get parameter
				if (checkedRequest().getQueryString() != null) {
					try {
						previousPageAbsolutePath = checkedRequest().getQueryString().substring(checkedRequest().getQueryString().lastIndexOf("path=")+5);
						} catch (StringIndexOutOfBoundsException e) {
							previousPageAbsolutePath = null;
						}
				}			
				
				//LoginJSON loginContainer = restTemplateJson().getForObject("http://vps111534.ovh.net/unm-backend/json/login?username=" + login.usernameField() + "&password=" + login.passwordField(), LoginJSON.class);
				AppToken token = sessionClient.login(apiKey, login.usernameField(), login.passwordField());
				
				if (token != null) {
					log.info("Standard login for user of id : " + token.getUser().getUid() + "(" + token.getId() + ")");
					if(token.getUser().getUid() == null) {
						setAttribute("errorMessage", "incorrect data");
					} else {
						log.info(jsonUrl + "/users/" + token.getUser().getUid());
						HttpHeaders headers = new HttpHeaders();
						headers.add("Authentication-Token", token.getId());
						HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
						
						User currentUser = restTemplate().exchange(jsonUrl + "/users/" + token.getUser().getUid(), HttpMethod.GET, entity, User.class).getBody();

						if (currentUser != null) {
							setSessionAttribute("currentUser", currentUser);
						}
						setSessionAttribute("authenticationToken", token.getId());
					}
				} else {
					setAttribute("errorMessage", "L'authentification a échoué, merci de vérifier votre user / mot de passe.");
				}
				
			}
			
			if (hasSessionAttribute("currentUser")) {
				//redirect to home or previous page
				if (previousPageAbsolutePath != null) {
					sendRedirect(previousPageAbsolutePath);
				} else {
					sendRedirect(getBaseURL()+"/");
				}
				return null;
			}

			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuAU", getMenuItems(jsonUrl, "AU"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			setAttribute("currentAbsolutePath", getAbsolutePath());
			
			return new View("login.jsp");
		}		
	}
	
	@HttpMethods("POST")
	private interface Login extends HttpInputs {


		@HttpRequired
		@HttpParameter(trim = true)
		String usernameField();
		
		@HttpRequired
		@HttpParameter(trim = true)
		String passwordField();
	}
}