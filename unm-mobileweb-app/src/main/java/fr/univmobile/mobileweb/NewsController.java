package fr.univmobile.mobileweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.Feed;
import fr.univmobile.mobileweb.models.FeedEmbedded;
import fr.univmobile.mobileweb.models.News;
import fr.univmobile.mobileweb.models.NewsEmbedded;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
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
			
			final SelectedFeeds selectedFeeds = getHttpInputs(SelectedFeeds.class);
			
			if (selectedFeeds.isHttpValid()) {
				// Get the List of news
				RestTemplate template = restTemplate();
				String url = "/news/search/findNewsForUniversityAndFeeds?universityId=" + getUniversity().getId();
				String[] feedsIds = selectedFeeds.feedsIds().split(",");
				setSessionAttribute("feedsIds", feedsIds);
				for (String feedId : feedsIds) {
					url += "&feedIds=" + feedId;
				}
				url += "&size=40";
				NewsEmbedded newsContainer = template.getForObject(jsonUrl + url, NewsEmbedded.class);
				
				News[] newsList = null;
				if (newsContainer._embedded != null) {
					//must be prevented somewhere else, because if news does not exist the view gonna be empty
					newsList = newsContainer._embedded.getNews();
				} else {
					newsList = new News[0];
				}
				setAttribute("newsList", newsList);
				return new View("newsajax.jsp");
			} else {
			
				// Get the List of feeds
				RestTemplate template = restTemplate();
				FeedEmbedded feedsContainer = template.getForObject(jsonUrl + "/feeds/search/findAllActiveRssFeedsForUniversity?universityId=" + getUniversity().getId(), FeedEmbedded.class);			
				Feed[] feedsList = null;
				if (feedsContainer._embedded != null) {
					//must be prevented somewhere else, because if news does not exist the view gonna be empty
					feedsList = feedsContainer._embedded.getFeeds();
				} else {
					feedsList = new Feed[0];
				}			
				
				// Get the list of news
				String url = "";
				String[] feedsIds = null;
				if (hasSessionAttribute("feedsIds")) {
					feedsIds = getSessionAttribute("feedsIds", String[].class);
					url = "/news/search/findNewsForUniversityAndFeeds?universityId=" + getUniversity().getId();
					for (String feedId : feedsIds) {
						url += "&feedIds=" + feedId;
					}
					url += "&size=40";
				} else {
					url = "/news/search/findNewsForUniversity?universityId=" + getUniversity().getId() + "&size=40";
				}
				NewsEmbedded newsContainer = template.getForObject(jsonUrl + url, NewsEmbedded.class);
				
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
				if (feedsIds != null) {
					List<Integer> iFeedsId = new ArrayList<Integer>();
					for (String feedId: feedsIds) {
						iFeedsId.add(Integer.parseInt(feedId));
					}
					setAttribute("feedsIds", iFeedsId);
				}
				setAttribute("feedsList", feedsList);
				
				return new View("news.jsp");
			}
		}
	}
	
	
	@HttpMethods("GET")
	private interface SelectedFeeds extends HttpInputs {


		@HttpRequired
		@HttpParameter(trim = true)
		String feedsIds();
	}
}