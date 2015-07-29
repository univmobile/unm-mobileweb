package fr.univmobile.mobileweb;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.Bookmark;
import fr.univmobile.mobileweb.models.BookmarkEmbedded;
import fr.univmobile.mobileweb.models.Category;
import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.User;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "bookmarks" })
public class BookmarksController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;
	protected final String universiteCategoryId;
	protected final String bonplansCategoryId;
	protected final String parisCategoryId;
	private int userId;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public BookmarksController(final String jsonUrl, final String universiteCategoryId, final String bonplansCategoryId, final String parisCategoryId) {

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
		
		userId = 0;
		
		if (!hasSessionAttribute("univ")) {
			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {
			
			if (!hasSessionAttribute("currentUser")) {
				//redirect to home or previous page
				sendRedirect(getBaseURL() + "/login?path="+getAbsolutePath());
				return null;
			} else {
				userId = getSessionAttribute("currentUser", User.class).getId();
			}
			
			// Get the list of bookmarks
			RestTemplate template = restTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authentication-Token", getSessionAttribute("authenticationToken", String.class));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
				
			BookmarkEmbedded bookmarksContainer = restTemplate().exchange(jsonUrl + "/users/" + userId + "/bookmarks", HttpMethod.GET, entity, BookmarkEmbedded.class).getBody();
			Bookmark[] bookmarksList = null;
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
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuAU", getMenuItems(jsonUrl, "AU"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			setAttribute("currentAbsolutePath", getAbsolutePath());
			
			//profile attributes
			setAttribute("bookmarksList", bookmarksList);
			
			return new View("bookmarks.jsp");
		}		
	}
}