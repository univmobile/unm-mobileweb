package fr.univmobile.mobileweb;

import java.io.IOException;
import java.util.ArrayList;

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
		
		//___________________________________________________________________________________________________________________________________
		//temporary for testing, delete later
		if (!hasSessionAttribute("univ")) {
			University univObj = restTemplate().getForObject(jsonUrl + "/universities/ " + 4, University.class);
			setSessionAttribute("univ", univObj);
			
		}
		//___________________________________________________________________________________________________________________________________
		
		if (!hasSessionAttribute("univ")) {
			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {
			
			if (!hasSessionAttribute("currentUser")) {
				//redirect to home or previous page
				sendRedirect(getBaseURL() + "/login");
				return null;
			} else {
				userId = getSessionAttribute("currentUser", User.class).getId();
			}
			
			// Get the list of bookmarks
			RestTemplate template = restTemplate();
			BookmarkEmbedded bookmarksContainer = template.getForObject(jsonUrl + "/users/" + userId + "/bookmarks", BookmarkEmbedded.class);
			Bookmark[] bookmarksList = null;
			if (bookmarksContainer._embedded != null) {
				//must be prevented somewhere else, because if bookmarks do not exist the view gonna be empty
				bookmarksList = bookmarksContainer._embedded.getBookmarks();
			} else {
				bookmarksList = new Bookmark[0];
			}
			
			//set pois of bookmarks
			for (Bookmark bookmark : bookmarksList) {
				Poi poi = template.getForObject(jsonUrl + "/bookmarks/" + bookmark.getId() + "/poi", Poi.class);
				bookmark.setPoi(poi);
				
				//set poi root category
				Category poiRootCategory = template.getForObject(jsonUrl + "/categories/" + poi.getCategoryId() + "/parent", Category.class);
				if (poiRootCategory != null) {
					bookmark.setPoiRootCategory(poiRootCategory);
				}
				
				//set map url
				if (Integer.toString(poiRootCategory.getId()).equals(universiteCategoryId)) {
					bookmark.setMapUrl("university-map#" + poi.getId());
				} else if (Integer.toString(poiRootCategory.getId()).equals(parisCategoryId)) {
					bookmark.setMapUrl("paris-map#" + poi.getId());
				} else if (Integer.toString(poiRootCategory.getId()).equals(bonplansCategoryId)) {
					bookmark.setMapUrl("goodplans-map#" + poi.getId());
				}
			}
			
			//remove bookmarks which are not of current university
			ArrayList<Bookmark> bookmarksArrayList = new ArrayList<Bookmark>();
			for (Bookmark bookmark : bookmarksList) {
				if (!Integer.toString(bookmark.getPoiRootCategory().getId()).equals(universiteCategoryId)) {
					bookmarksArrayList.add(bookmark);
				} else if (bookmark.getPoi().getUniversityId() == getUniversity().getId()) {
					bookmarksArrayList.add(bookmark);
				}
			}
			bookmarksList = bookmarksArrayList.toArray(new Bookmark[bookmarksArrayList.size()]);
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			
			//profile attributes
			setAttribute("bookmarksList", bookmarksList);
			
			return new View("bookmarks.jsp");
		}		
	}
}