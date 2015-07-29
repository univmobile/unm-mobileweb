package fr.univmobile.mobileweb;

import java.io.IOException;

import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.News;
import fr.univmobile.mobileweb.models.NewsEmbedded;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "news" })
public class NewsController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public NewsController(final String jsonUrl) {

		this.jsonUrl = jsonUrl;
	}


	/********************************************************
	 * This method is called automatically on /news view
	 ********************************************************/
	@Override
	public View action() throws IOException {
		
		if (!hasSessionAttribute("univ")) {

			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {
			
			// Get the list of news
			RestTemplate template = restTemplate();
			NewsEmbedded newsContainer = template.getForObject(jsonUrl + "/news/search/findNewsForUniversity?universityId=" + getUniversity().getId() + "&size=40", NewsEmbedded.class);
			
			News[] newsList = null;
			if (newsContainer._embedded != null) {
				//must be prevented somewhere else, because if news does not exist the view gonna be empty
				newsList = newsContainer._embedded.getNews();
			} else {
				newsList = new News[0];
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
			
			//news attributes
			setAttribute("newsList", newsList);
			
			return new View("news.jsp");
		}		
	}
}