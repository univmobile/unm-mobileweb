package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;
import static fr.univmobile.backend.client.RegionsUtils.getUniversityById;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.univmobile.backend.client.AppToken;
import fr.univmobile.backend.client.ClientException;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.SessionClient;
import fr.univmobile.mobileweb.models.Category;
import fr.univmobile.mobileweb.models.CategoryEmbedded;
import fr.univmobile.mobileweb.models.News;
import fr.univmobile.mobileweb.models.NewsEmbedded;
import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.PoiEmbedded;
import fr.univmobile.mobileweb.models.Region;
import fr.univmobile.mobileweb.models.RegionEmbedded;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.UniversityEmbedded;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "" })
public class HomeController extends AsbtractMobileWebJspController {

	public HomeController(final String jsonUrl, String universiteCategoryId, final String apiKey,
			final SessionClient sessionClient, final RegionClient regions) {

		this.jsonUrl = jsonUrl;
		this.universiteCategoryId = universiteCategoryId;
		this.apiKey = checkNotNull(apiKey, "apiKey");
		this.sessionClient = checkNotNull(sessionClient, "sessionClient");
		this.regions = checkNotNull(regions, "regions");
		this.categoriesIconsUrl = "https://univmobile-dev.univ-paris1.fr/testSP/api/files/categoriesicons/";
	}

	private final String jsonUrl;
	private final String apiKey;
	private final SessionClient sessionClient;
	private final RegionClient regions;
	private final String universiteCategoryId;
	protected final String categoriesIconsUrl;
	
	private static final Log log = LogFactory.getLog(HomeController.class);

	@Override
	public View action() throws IOException {

		final SelectedUniversity selected = getHttpInputs(SelectedUniversity.class);

		if (selected.isHttpValid()) {

			final String univ = selected.univ();
			
			University univObj = restTemplate().getForObject(jsonUrl + "/universities/ " + univ, University.class);

			setSessionAttribute("univ", univObj);
		}
		
		//a.k.a. if(getUniversity() == null)
		if (hasSessionAttribute("univ")) {
			
			// Get the list of news
			RestTemplate template = restTemplate();
			NewsEmbedded newsContainer = template.getForObject(jsonUrl + "/news/search/findNewsForUniversity?universityId=" + getUniversity().getId() + "&size=5", NewsEmbedded.class);
			
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
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			setAttribute("currentAbsolutePath", getAbsolutePath());
			
			//home attributes
			setAttribute("newsList", newsList);
			setAttribute("mapUrl", generateMapUrl(getUniversity()));
			setAttribute("isIDF", getUniversity().getRegionId() == 1);
			
			return new View("home.jsp");
		} else {
		
		
			// Get the list of region
			RestTemplate template = restTemplate();
			
			RegionEmbedded regionContainer = template.getForObject(jsonUrl + "/regions", RegionEmbedded.class);
			setAttribute("regionsList", regionContainer._embedded.getRegions());
			
			int i = 0;
			for (Region region : regionContainer._embedded.getRegions()) {
				i++;
				UniversityEmbedded universityContainer = template.getForObject(jsonUrl + "/regions/" + i/*region.getId()*/ + "/universities", UniversityEmbedded.class);
				region.setUniversities(universityContainer._embedded.getUniversities());
			}
			
			return new View("splashscreen.jsp");
		}
	}
	
	@HttpMethods("POST")
	private interface SelectedUniversity extends HttpInputs {


		@HttpRequired
		@HttpParameter(trim = true)
		String univ();
	}
	
	protected Map<Integer, Category> provideCategories() {
		
		RestTemplate template = restTemplate();
		CategoryEmbedded categoryContainer = template.getForObject(jsonUrl + "/categories/"+ universiteCategoryId +"/children", CategoryEmbedded.class);
		if (categoryContainer._embedded != null) {
			Map<Integer, Category> categoriesMap = new HashMap<Integer, Category>();
			for (Category category : categoryContainer._embedded.getCategories()) {
				categoriesMap.put(category.getId(), category);
			}
			return categoriesMap;
		} else {
			return null;
		}
	}
	
	private String generateMapUrl(University university) {
		Map<Integer, Category> categoriesMap = provideCategories();
		
		String base = "https://maps.googleapis.com/maps/api/staticmap?";
		String center = "center="+university.getCentralLat()+","+university.getCentralLng();
		String zoom = "&zoom=16";
		String size = "&size=640x400";
		String markers = "&markers=";
		
		// Get the list of pois
		RestTemplate template = restTemplate();
		PoiEmbedded poiContainer = template.getForObject(jsonUrl + "/pois/search/findByUniversityAndCategoryRoot?universityId=" + university.getId() + "&categoryId=" + universiteCategoryId+"&size=99", PoiEmbedded.class);
		
		if (poiContainer._embedded != null) {
			for (Poi poi : poiContainer._embedded.getPois()) {
				if (poi.isActive() && poi.getLat() != 0 && poi.getLng() != 0) {
					// Get the category object of the poi
					Category category = categoriesMap.get(poi.getCategoryId());
					String icon = null;
					String markerPoint = "";
					//try {
					//	if (category.getMarkerIconUrl() != null) {
					//		markerPoint += "icon:"+URLEncoder.encode(this.categoriesIconsUrl+category.getActiveIconUrl(), "UTF-8")+"|";
					//	}
						markerPoint += poi.getLat() + "," + poi.getLng();
						markers += "|" + markerPoint;
					//} catch (UnsupportedEncodingException ex) {
					//	log.error(ex);
					//}
				}
			}
		}
		
		return base+center+zoom+size+markers;
	}
}
