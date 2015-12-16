package fr.univmobile.mobileweb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.Category;
import fr.univmobile.mobileweb.models.CategoryEmbedded;
import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.PoiEmbedded;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;

@Paths({ "university-map" })
public class UniversityMapController extends AbstractMapController {
	
	private final String universiteCategoryId;
	private final String librariesCategoryId;
	private final Map<Long, University> universities = new HashMap<Long, University>();
	
	public UniversityMapController(String jsonUrl, String universiteCategoryId, String restaurationUniversitaireCategoryId, String librariesCategoryId) {
		super(jsonUrl, restaurationUniversitaireCategoryId);
		this.universiteCategoryId = universiteCategoryId;
		this.librariesCategoryId = librariesCategoryId;
	}
	
	@Override
	protected String getLibrariesCategoryId() {
		return librariesCategoryId;
	}
	
	@Override
	protected String getCategoryRootId() {
		return universiteCategoryId;
	}

	@Override
	protected String provideViewName() {
		final ShowExternalized showExternalized = getHttpInputs(ShowExternalized.class);
		
		if (showExternalized.isHttpValid() && (showExternalized.external().equals("true") || showExternalized.external().equals("1"))) {
			return "university-map-ext.jsp";
		} else {
			return "university-map.jsp";
		}
	}

	@Override
	protected Poi[] providePois() {

		RestTemplate template = restTemplate();
		PoiEmbedded poiContainer = template.getForObject(jsonUrl + "/pois/search/findByUniversityAndCategoryRoot?universityId=" + getUniversity().getId()+"&categoryId=" + universiteCategoryId + "&size=800", PoiEmbedded.class);
		if (poiContainer._embedded != null) {	
			return poiContainer._embedded.getPois();
		} else {
			return null;
		}
	}

	@Override
	protected Category[] provideCategories() {
		
		RestTemplate template = restTemplate();
		CategoryEmbedded categoryContainer = template.getForObject(jsonUrl + "/categories/"+ universiteCategoryId +"/children", CategoryEmbedded.class);
		if (categoryContainer._embedded != null) {		
			return categoryContainer._embedded.getCategories();
		} else {
			return null;
		}
	}
	
	/**
	 * Gets currently selected university
	 */
	@Override
	public University getUniversity() {
		final ShowExternalized showExternalized = getHttpInputs(ShowExternalized.class);
		
		if (showExternalized.isHttpValid() && (showExternalized.external().equals("true") || showExternalized.external().equals("1"))) {
			University university = null;
			if (!this.universities.containsKey(showExternalized.universityId())) {
				university = restTemplate().getForObject(jsonUrl + "/universities/" + String.valueOf(showExternalized.universityId()), University.class);
				if (university.getId() == 0) {
					university = null;
				} else {
					this.universities.put(showExternalized.universityId(), university);
				}
			} else {
				university = this.universities.get(showExternalized.universityId());
			}
			return university;
		} else {
			return super.getUniversity();
		}
	}

	@HttpMethods("GET")
	private interface ShowExternalized extends HttpInputs {
		
		@HttpRequired
		@HttpParameter(trim = true)
		String external();

		@HttpRequired
		@HttpParameter(trim = true)
		Long universityId();
		
	}
	
}
