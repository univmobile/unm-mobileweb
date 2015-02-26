package fr.univmobile.mobileweb;

import java.io.IOException;

import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.UniversityLibrary;
import fr.univmobile.mobileweb.models.UniversityLibraryEmbedded;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "libraries" })
public class LibrariesController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public LibrariesController(final String jsonUrl) {

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
			
			// Get the list of libraries
			RestTemplate template = restTemplate();
			UniversityLibraryEmbedded librariesContainer = template.getForObject(jsonUrl + "/universityLibraries/search/findByUniversity?universityId=" + getUniversity().getId() + "&size=5", UniversityLibraryEmbedded.class);
			UniversityLibrary[] librariesList = null;
			if (librariesContainer._embedded != null) {
				//must be prevented somewhere else, because if libraries do not exist the view gonna be empty
				librariesList = librariesContainer._embedded.getUniversityLibraries();
			} else {
				librariesList = new UniversityLibrary[0];
			}
			
			//set pois of libraries
			for (UniversityLibrary library : librariesList) {
				Poi poi = template.getForObject(jsonUrl + "/universityLibraries/" + library.getId() + "/poi", Poi.class);
				library.setPoi(poi);
			}
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			
			//libraries attributes
			setAttribute("librariesList", librariesList);
			
			return new View("libraries.jsp");
		}		
	}
}
