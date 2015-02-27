package fr.univmobile.mobileweb;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.LoginJSON;
import fr.univmobile.mobileweb.models.NewsEmbedded;
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
	
	protected final String jsonUrl;
	private String previousPageAbsolutePath;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public LoginController(final String jsonUrl) {

		this.jsonUrl = jsonUrl;
	}


	/********************************************************
	 * This method is called automatically on /login view
	 ********************************************************/
	@Override
	public View action() throws IOException {
		
		RestTemplate template = restTemplate();
		
		//___________________________________________________________________________________________________________________________________
		//temporary for testing, delete later
		if (!hasSessionAttribute("univ")) {
			University univObj = template.getForObject(jsonUrl + "/universities/ " + 13, University.class);
			setSessionAttribute("univ", univObj);
		}
		//___________________________________________________________________________________________________________________________________		
		
		
		
		if (!hasSessionAttribute("univ")) {

			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {	
			
			previousPageAbsolutePath = null;
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
				
				LoginJSON loginContainer = restTemplateJson().getForObject("http://vps111534.ovh.net/unm-backend/json/login?username=" + login.usernameField() + "&password=" + login.passwordField(), LoginJSON.class);
				if(loginContainer.getId() == null) {
					setAttribute("errorMessage", "incorrect data");
				} else {
					User currentUser = template.getForObject(jsonUrl + "/users/ " + loginContainer.getId(), User.class);
					if (currentUser != null) {
						setSessionAttribute("currentUser", currentUser);
					}
					setSessionAttribute("authenticationToken", loginContainer.getAuthenticationToken());
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