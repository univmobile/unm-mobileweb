package fr.univmobile.mobileweb;

import java.io.IOException;

import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.Menu;
import fr.univmobile.mobileweb.models.MenuEmbedded;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "menu-content" })
public class MenuContentController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public MenuContentController(final String jsonUrl) {

		this.jsonUrl = jsonUrl;
	}


	/********************************************************
	 * This method is called automatically on /news view
	 ********************************************************/
	@Override
	public View action() throws IOException {
		
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
					
			final SelectedMenu selected = getHttpInputs(SelectedMenu.class);
			if (selected.isHttpValid()) {				
				if(selected.menuId().equals("")) {
					sendError404();
				}	
			} else {
				sendError404();
			}
			
			String menuContent = "";
			
			RestTemplate template = restTemplate();
			MenuEmbedded menuContainer = template.getForObject(jsonUrl + "/menues/search/findAll", MenuEmbedded.class);			
			if (menuContainer._embedded != null) {
				for (Menu menuItem : menuContainer._embedded.getMenu()) {
					if (Integer.toString(menuItem.getId()).equals(selected.menuId()) ) {
						menuContent = menuItem.getContent();
						break;
					}
				}
			}
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			
			//menuContent attributes
			setAttribute("menuContent", menuContent);
			
			return new View("menu-content.jsp");
		}		
	}
	
	@HttpMethods("GET")
	private interface SelectedMenu extends HttpInputs {


		@HttpRequired
		@HttpParameter(trim = true)
		String menuId();
	}
}