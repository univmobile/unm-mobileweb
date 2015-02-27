package fr.univmobile.mobileweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univmobile.mobileweb.models.Category;
import fr.univmobile.mobileweb.models.CommentJson;
import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.PoiJson;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.User;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.View;

public abstract class AbstractMapController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;
	protected final String restaurationUniversitaireCategoryId;
	protected final String categoriesIconsUrl;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public AbstractMapController(final String jsonUrl, final String restaurationUniversitaireCategoryId) {

		this.jsonUrl = jsonUrl;
		this.restaurationUniversitaireCategoryId = restaurationUniversitaireCategoryId;
		categoriesIconsUrl = "http://vps111534.ovh.net/unm-backend/categoriesicons/";
	}


	/********************************************************
	 * This method is called automatically on #provideViewName() view
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
		
		final Comment comment = getHttpInputs(Comment.class);
		if (comment.isHttpValid()) {
			try {
				sendPostAddComment(comment.commentMessage(), comment.poiId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		final BookmarkPoi bookmarkPoi = getHttpInputs(BookmarkPoi.class);
		if (bookmarkPoi.isHttpValid()) {
			if (hasSessionAttribute("currentUser")) {
				try {
					sendPostAddBookmark(bookmarkPoi.poiIdBookmark(), getSessionAttribute("currentUser", User.class).getId());
					sendRedirect(getBaseURL()+"/bookmarks");
					return null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		final AddPoi addPoi = getHttpInputs(AddPoi.class);
		if (addPoi.isHttpValid()) {
			
			int currentUserId = getSessionAttribute("currentUser", User.class).getId();
			University userUniversity = restTemplate().getForObject(jsonUrl + "/users/ " + currentUserId + "/university", University.class);
			
			PoiJson poiJson = new PoiJson(true, addPoi.poiName(), jsonUrl+"/categories/"+addPoi.poiCategoryId(), jsonUrl+"/universities/"+userUniversity.getId());
			poiJson.setAddress(addPoi.poiAddress());
			poiJson.setPhones(addPoi.poiPhone());
			poiJson.setEmail(addPoi.poiEmail());
			poiJson.setDescription(addPoi.poiDescription());
			
			try {
				sendPostAddPoi(poiJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (!hasSessionAttribute("univ")) {		

			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {
			
			Poi[] pois = providePois();
			if (pois != null) {		
				pois = filterPois(pois);
				setAttribute("allPois", pois);
			}

			Category[] categories = provideCategories();
			if (categories != null) {			
				categories = filterCategories(categories);
				setAttribute("allCategories", categories);
			}
			
			//assign category object to poi
			if (pois != null && categories != null) {
				for (Poi poi : pois) {
					for(Category category : categories) {
						if (poi.getCategoryId() == category.getId()) {
							poi.setCategory(category);
							break;
						}
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
			setAttribute("currentAbsolutePath", getAbsolutePath());
			
			//map attributes
			setAttribute("API_KEY", "AIzaSyC8uD1y1Dgx0W6JJMCQm7V1OJx_nsbRmBE");
			setAttribute("mapHeight", "400px");
			setAttribute("restaurationUniversitaireCategoryId", restaurationUniversitaireCategoryId);
			setAttribute("categoriesIconsUrl", categoriesIconsUrl);
			if (getLibrariesCategoryId() != null) {
				setAttribute("librariesCategoryId", getLibrariesCategoryId());
			}
			if (getCategoryRootId() != null) {
				setAttribute("categoryRootId", getCategoryRootId());
			}
			
			return new View(provideViewName());
		}	
	}
	
	protected Poi[] filterPois(Poi[] pois) {
		
		ArrayList<Poi> filteredPois = new ArrayList<Poi>();
		for (Poi poiItem : pois) {
			if (poiItem.isActive() && poiItem.getLat() != 0 && poiItem.getLng() != 0) {
				filteredPois.add(poiItem);
			}
		}
		return (Poi[]) filteredPois.toArray(new Poi[filteredPois.size()]);
	}
	
	protected Category[] filterCategories(Category[] categories) {
		
		ArrayList<Category> filteredCategories = new ArrayList<Category>();
		for (Category categoryItem : categories) {
			if (categoryItem.isActive()) {
				filteredCategories.add(categoryItem);
			}
		}
		return (Category[]) filteredCategories.toArray(new Category[filteredCategories.size()]);
	}
	
	protected abstract String provideViewName();
	protected abstract Poi[] providePois();
	protected abstract Category[] provideCategories();

	//should be overridden, if id exist
	protected String getLibrariesCategoryId() {
		return null;
	}
	
	//should be overridden, if id exist
		protected String getCategoryRootId() {
			return null;
		}
	
	@HttpMethods("POST")
	private interface Comment extends HttpInputs {

		@HttpRequired
		@HttpParameter(trim = true)
		String commentMessage();
		
		@HttpRequired
		@HttpParameter(trim = true)
		String poiId();
	}
	
	@HttpMethods("POST")
	private interface BookmarkPoi extends HttpInputs {
		
		@HttpRequired
		@HttpParameter(trim = true)
		String poiIdBookmark();
	}
	
	@HttpMethods("POST")
	private interface AddPoi extends HttpInputs {
		
		@HttpRequired
		@HttpParameter(trim = true)
		String poiName();
		
		@HttpRequired
		@HttpParameter(trim = true)
		String poiCategoryId();
		
		@HttpRequired
		@HttpParameter(trim = true)
		String poiAddress();
		
		@HttpParameter(trim = true)
		String poiPhone();
		
		@HttpParameter(trim = true)
		String poiEmail();
		
		@HttpRequired
		@HttpParameter(trim = true)
		String poiDescription();
	}
	
	// HTTP POST request
	private void sendPostAddComment(String commentMessage, String poiId) throws Exception {
 
		if (!poiId.equals("")) {
			String url = jsonUrl + "/comments/";
			 
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
	 
			// add header		
			post.addHeader("Content-Type", "application/json");
			post.addHeader("Authentication-Token", getSessionAttribute("authenticationToken", String.class));
			
			CommentJson commentJson = new CommentJson("", commentMessage, "true", jsonUrl + "/pois/" + poiId);
			ObjectMapper mapper = new ObjectMapper();
			byte[] inputByteArray = mapper.writeValueAsBytes(commentJson);
	 
			post.setEntity(new ByteArrayEntity(inputByteArray));
	 	
			HttpResponse response = client.execute(post);
			
			if (response.getStatusLine().getStatusCode() == 201) {
				setAttribute("statusSuccess", "Comment created successfully.");
			} else {
				setAttribute("statusFail", response.getStatusLine());
			}

		}
	}
	
	// HTTP POST request
	private void sendPostAddBookmark(String poiId, int userId) throws Exception {
 
		if (!poiId.equals("") && userId != 0) {
			String url = jsonUrl + "/bookmarks/";
			 
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
	 
			// add header		
			post.addHeader("Content-Type", "application/json");
			
			String userUrl = jsonUrl + "/users/" + userId;
			String poiUrl = jsonUrl + "/pois/" + poiId;
			StringEntity input = new StringEntity("{\"user\":\"" + userUrl + "\",\"poi\":\"" + poiUrl + "\"}");
			input.setContentType("application/json");
	 
			post.setEntity(input);
	 
			client.execute(post);
		}
	}
	
	// HTTP POST request
	private void sendPostAddPoi(PoiJson poiJson) throws Exception {
 
		String url = jsonUrl + "/pois/";
		 
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
 
		// add header		
		post.addHeader("Content-Type", "application/json");
		post.addHeader("Authentication-Token", getSessionAttribute("authenticationToken", String.class));
		
		ObjectMapper mapper = new ObjectMapper();
		byte[] inputByteArray = mapper.writeValueAsBytes(poiJson);
 
		post.setEntity(new ByteArrayEntity(inputByteArray));
		
		client.execute(post);
	}
}
