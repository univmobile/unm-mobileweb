package fr.univmobile.mobileweb;

import java.io.IOException;
import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.Link;
import fr.univmobile.mobileweb.models.LinkEmbedded;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "media" })
public class MediaController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;

	private int userId;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public MediaController(final String jsonUrl) {

		this.jsonUrl = jsonUrl;
	}


	/********************************************************
	 * This method is called automatically on /profile view
	 ********************************************************/
	@Override
	public View action() throws IOException {
		
		//___________________________________________________________________________________________________________________________________
		//temporary for testing, delete later
		if (!hasSessionAttribute("univ")) {
			University univObj = restTemplate().getForObject(jsonUrl + "/universities/ " + 1, University.class);
			setSessionAttribute("univ", univObj);
			
		}
		//___________________________________________________________________________________________________________________________________
		
		if (!hasSessionAttribute("univ")) {
			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {
			
			// Get the list of links
			RestTemplate template = restTemplate();
			LinkEmbedded linksContainer = template.getForObject(jsonUrl + "/links/search/findByUniversity?universityId=" + getUniversity().getId() + "&size=200", LinkEmbedded.class);
			
			Link[] linksList = null;
			if (linksContainer._embedded != null) {
				//must be prevented somewhere else, because if links do not exist the view gonna be empty
				linksList = linksContainer._embedded.getLinks();
			} else {
				linksList = new Link[0];
			}
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			setAttribute("currentAbsolutePath", getAbsolutePath());
			
			//media attributes
			setAttribute("linksList", linksList);
			
			return new View("media.jsp");
		}		
	}
}
