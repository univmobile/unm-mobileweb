package fr.univmobile.mobileweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.Bookmark;
import fr.univmobile.mobileweb.models.BookmarkEmbedded;
import fr.univmobile.mobileweb.models.Category;
import fr.univmobile.mobileweb.models.Link;
import fr.univmobile.mobileweb.models.LinkEmbedded;
import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.UniversityEmbedded;
import fr.univmobile.mobileweb.models.UniversityLibrary;
import fr.univmobile.mobileweb.models.UniversityLibraryEmbedded;
import fr.univmobile.mobileweb.models.UsageStats;
import fr.univmobile.mobileweb.models.User;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "profile" })
public class ProfileController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;
	protected final String universiteCategoryId;
	protected final String bonplansCategoryId;
	protected final String parisCategoryId;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public ProfileController(final String jsonUrl, final String universiteCategoryId, final String bonplansCategoryId, final String parisCategoryId) {

		this.jsonUrl = jsonUrl;
		this.universiteCategoryId = universiteCategoryId;
		this.bonplansCategoryId = bonplansCategoryId;
		this.parisCategoryId = parisCategoryId;
	}


	/********************************************************
	 * This method is called automatically on /profile view
	 ********************************************************/
	@Override
	public View action() throws IOException {
		
		final SelectedUniversity selected = getHttpInputs(SelectedUniversity.class);
		if (selected.isHttpValid()) {
			final String universityId = selected.universityId();			
			University univObj = restTemplate().getForObject(jsonUrl + "/universities/ " + universityId, University.class);
			setSessionAttribute("univ", univObj);
			// We track the selected university
			UsageStats usage = new UsageStats();
			usage.setSource("W");
			usage.setUniversity(jsonUrl + "/universities/" + universityId);
			restTemplate().postForObject(jsonUrl + "/usageStats", usage, Object.class);

		}
		
		if (!hasSessionAttribute("univ")) {
			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {
			Integer userId = null;
			if (!hasSessionAttribute("currentUser")) {
				//redirect to home or previous page
				//sendRedirect(getBaseURL() + "/login?path="+getAbsolutePath());
				// return null;
			} else {
				userId = getSessionAttribute("currentUser", User.class).getId();
			}
			
			// Get the list of links
			RestTemplate template = restTemplate();
			LinkEmbedded linksContainer = template.getForObject(jsonUrl + "/links/search/findByUniversity?universityId=" + getUniversity().getId() + "&size=5", LinkEmbedded.class);
			
			Link[] linksList = null;
			if (linksContainer._embedded != null) {
				//must be prevented somewhere else, because if links do not exist the view gonna be empty
				linksList = linksContainer._embedded.getLinks();
			} else {
				linksList = new Link[0];
			}
			
			// Get the list of libraries
			UniversityLibraryEmbedded librariesContainer = template.getForObject(jsonUrl + "/universityLibraries/search/findByUniversity?universityId=" + getUniversity().getId() + "&size=5", UniversityLibraryEmbedded.class);
			UniversityLibrary[] librariesList = null;
			if (librariesContainer._embedded != null) {
				//must be prevented somewhere else, because if libraries do not exist the view gonna be empty
				librariesList = librariesContainer._embedded.getUniversityLibraries();
			} else {
				librariesList = new UniversityLibrary[0];
			}
			
			Bookmark[] bookmarksList = null;
			if (userId != null) {
			// Get the list of bookmarks
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authentication-Token", getSessionAttribute("authenticationToken", String.class));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
				
			BookmarkEmbedded bookmarksContainer = restTemplate().exchange(jsonUrl + "/users/" + userId + "/bookmarks", HttpMethod.GET, entity, BookmarkEmbedded.class).getBody();

			if (bookmarksContainer._embedded != null) {
				//must be prevented somewhere else, because if bookmarks do not exist the view gonna be empty
				bookmarksList = bookmarksContainer._embedded.getBookmarks();
			} else {
				bookmarksList = new Bookmark[0];
			}
			
			//set map url
			for (Bookmark bookmark : bookmarksList) {
				if (Integer.toString(bookmark.getRootCategoryId()).equals(universiteCategoryId)) {
					bookmark.setMapUrl("university-map#" + bookmark.getPoiId());
				} else if (Integer.toString(bookmark.getRootCategoryId()).equals(parisCategoryId)) {
					bookmark.setMapUrl("paris-map#" + bookmark.getPoiId());
				} else if (Integer.toString(bookmark.getRootCategoryId()).equals(bonplansCategoryId)) {
					bookmark.setMapUrl("goodplans-map#" + bookmark.getPoiId());
				}
			}
			
			//remove bookmarks which are not of current university
			ArrayList<Bookmark> bookmarksArrayList = new ArrayList<Bookmark>();
			for (Bookmark bookmark : bookmarksList) {
				if (!Integer.toString(bookmark.getRootCategoryId()).equals(universiteCategoryId)) {
					bookmarksArrayList.add(bookmark);
				} else if (bookmark.getPoiUniversityId() == getUniversity().getId()) {
					bookmarksArrayList.add(bookmark);
				}
			}
			bookmarksList = bookmarksArrayList.toArray(new Bookmark[bookmarksArrayList.size()]);
			
			//trim array
			if (bookmarksList.length > 3) {
				bookmarksList = Arrays.copyOf(bookmarksList, 3);
			}
			} else {
				bookmarksList = new Bookmark[0];
			}
			
			//get list of all universities
			UniversityEmbedded universityContainer = template.getForObject(jsonUrl + "/universities/search/findAllActiveWithoutCrousByRegion?regionId=" + getUniversity().getRegionId(), UniversityEmbedded.class);
			University[] universitiesList = null;
			if (universityContainer._embedded != null) {
				//must be prevented somewhere else, because if universites do not exist the view gonna be empty
				universitiesList = universityContainer._embedded.getUniversities();
			} else {
				universitiesList = new University[0];
			}
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuAU", getMenuItems(jsonUrl, "AU"));
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			setAttribute("currentAbsolutePath", getAbsolutePath());
			
			//profile attributes
			setAttribute("linksList", linksList);
			setAttribute("librariesList", librariesList);
			setAttribute("bookmarksList", bookmarksList);
			setAttribute("universitiesList", universitiesList);
			
			return new View("profile.jsp");
		}		
	}
	
	@HttpMethods("GET")
	private interface SelectedUniversity extends HttpInputs {

		@HttpRequired
		@HttpParameter(trim = true)
		String universityId();
	}
}
