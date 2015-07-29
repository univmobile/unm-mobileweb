package fr.univmobile.mobileweb;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;

import fr.univmobile.mobileweb.models.Category;
import fr.univmobile.mobileweb.models.CommentJson;
import fr.univmobile.mobileweb.models.Menu;
import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.PoiEmbedded;
import fr.univmobile.mobileweb.models.PoiJson;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.UsageStats;
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
	private final Geocoder geocoder;
	
	private static final Log log = LogFactory
			.getLog(AbstractMapController.class);

	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public AbstractMapController(final String jsonUrl, final String restaurationUniversitaireCategoryId) {

		this.jsonUrl = jsonUrl;
		this.restaurationUniversitaireCategoryId = restaurationUniversitaireCategoryId;
		categoriesIconsUrl = "http://univmobile-dev.univ-paris1.fr/admin/files/categoriesicons/";
		geocoder = new Geocoder();
	}


	/********************************************************
	 * This method is called automatically on #provideViewName() view
	 ********************************************************/
	@Override
	public View action() throws IOException {
		
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
					sendRedirect(getBaseURL()+"/profile");
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
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authentication-Token", getSessionAttribute("authenticationToken", String.class));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
				
			University userUniversity = restTemplate().exchange(jsonUrl + "/users/" + currentUserId + "/university", HttpMethod.GET, entity, University.class).getBody();

			PoiJson poiJson = new PoiJson(true, addPoi.poiName(), jsonUrl+"/categories/"+addPoi.poiCategoryId(), jsonUrl+"/universities/"+userUniversity.getId());
			poiJson.setAddress(addPoi.poiAddress());
			poiJson.setCity(addPoi.poiCity());
			// Try to get the coordinates :
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(addPoi.poiAddress() + ", " + addPoi.poiCity()).setLanguage("fr").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
			if (geocoderResponse.getResults() != null && geocoderResponse.getResults().size() > 0 && geocoderResponse.getResults().get(0).getGeometry()!= null
					&& geocoderResponse.getResults().get(0).getGeometry().getLocation() != null) {
				poiJson.setLat(geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue());
				poiJson.setLng(geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue());
			}
			log.info("Coordianate got by google geocoder for address '" + addPoi.poiAddress() + "' : " + poiJson.getLat() + " , " + poiJson.getLng());
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
		
		final OpenPoi openPoi = getHttpInputs(OpenPoi.class);
		if (openPoi.isHttpValid()) {
			// try to get the poi
			Poi poi = restTemplate().getForObject(jsonUrl + "/pois/" + openPoi.poi(), Poi.class);
			
			if (poi != null && poi.getId() == Integer.valueOf(openPoi.poi())) {
				int universityId = poi.getUniversityId();
				if (universityId > 0) {
					boolean selectUniversity = true;
					if (hasSessionAttribute("univ")) {
						University selectedUniv = getSessionAttribute("univ", University.class);
						if (selectedUniv.getId() == universityId) {
							// The selected university is the correct one, nothing to do...
							selectUniversity = false;
						}
					}
					if (selectUniversity) {
						setUniversity(jsonUrl, universityId);
					}
				}
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
			Category globalBiblioCat = restTemplate().getForObject(jsonUrl + "/categories/4", Category.class);
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
					if (poi.getCategoryId() == 4) {
						poi.setCategory(globalBiblioCat);
					}
				}
			}		
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuAU", getMenuItems(jsonUrl, "AU"));
			Menu[] ttMenus = getMenuItems(jsonUrl, "TT");
			setAttribute("menuTT", ttMenus);
			int nbTTMenus = 0;
			if (ttMenus != null) {
				for (Menu ttMenu : ttMenus) {
					if (ttMenu.getId() == 20) {
						setAttribute("isUnivMap", Boolean.TRUE);
						nbTTMenus++;
					}
					if (ttMenu.getId() == 21) {
						setAttribute("isParisMap", Boolean.TRUE);
						nbTTMenus++;
					}
					if (ttMenu.getId() == 22) {
						setAttribute("isBB", Boolean.TRUE);
						nbTTMenus++;
					}
				}
			}
			setAttribute("nbTTMenus", nbTTMenus);
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			setAttribute("currentAbsolutePath", getAbsolutePath());
			
			//map attributes
			setAttribute("API_KEY", "AIzaSyBLdHFXnqofrZMnGb5W5NDnQKK7amivZS4");
			setAttribute("mapHeight", "400px");
			setAttribute("restaurationUniversitaireCategoryId", restaurationUniversitaireCategoryId);
			setAttribute("categoriesIconsUrl", categoriesIconsUrl);
			if (getLibrariesCategoryId() != null) {
				setAttribute("librariesCategoryId", getLibrariesCategoryId());
			}
			if (getCategoryRootId() != null) {
				setAttribute("categoryRootId", getCategoryRootId());
			}
			setAttribute("isIDF", getUniversity().getRegionId() == 1);
			
			
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
	
	@HttpMethods("GET")
	private interface OpenPoi extends HttpInputs {

		@HttpRequired
		@HttpParameter(trim = true)
		String poi();
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
		
		@HttpRequired
		@HttpParameter(trim = true)
		String poiCity();
		
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
			
			if (response.getStatusLine().getStatusCode() == 201 || response.getStatusLine().getStatusCode() == 200) {
				setAttribute("statusSuccess", "Votre commentaire a été créé avec succès.");
			} else {
				setAttribute("statusFail", response.getStatusLine());
			}

		}
	}
	
	// HTTP POST request
	private void sendPostAddBookmark(String poiId, int userId) throws Exception {
		log.info("poi ID :" + poiId + " ; user ID : " + String.valueOf(userId));
		if (!poiId.equals("") && userId != 0) {
			String url = jsonUrl + "/bookmarks/";
			 
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
	 
			// add header		
			post.addHeader("Content-Type", "application/json");
			post.addHeader("Authentication-Token", getSessionAttribute("authenticationToken", String.class));
			
			
			String userUrl = jsonUrl + "/users/" + userId;
			String poiUrl = jsonUrl + "/pois/" + poiId;
			StringEntity input = new StringEntity("{\"user\":\"" + userUrl + "\",\"poi\":\"" + poiUrl + "\"}");
			input.setContentType("application/json");
	 
			post.setEntity(input);
	 
			HttpResponse response = client.execute(post);
			
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
