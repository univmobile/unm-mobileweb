package fr.univmobile.mobileweb;

import java.io.IOException;

import fr.univmobile.mobileweb.models.University;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "profile" })
public class ProfileController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public ProfileController(final String jsonUrl) {

		this.jsonUrl = jsonUrl;
	}


	/********************************************************
	 * This method is called automatically on /profile view
	 ********************************************************/
	@Override
	public View action() throws IOException {
		
		/* check login information here
		if (!hasSessionAttribute("user")) {
			//redirect to login
		}
		*/
		
		//___________________________________________________________________________________________________________________________________
		//temporary for testing, delete later
		if (!hasSessionAttribute("univ")) {
			University univObj = restTemplate().getForObject(jsonUrl + "/universities/ " + 13, University.class);
			setSessionAttribute("univ", univObj);
		}
		//___________________________________________________________________________________________________________________________________
		
		if (!hasSessionAttribute("univ")) {

			sendRedirect(getBaseURL());
			return null;
		}  else {
			
			
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			
			return new View("profile.jsp");
		}		
	}
}
