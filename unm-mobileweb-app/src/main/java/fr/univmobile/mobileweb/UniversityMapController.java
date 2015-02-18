package fr.univmobile.mobileweb;

import org.springframework.web.client.RestTemplate;

import fr.univmobile.web.commons.Paths;

@Paths({ "university-map" })
public class UniversityMapController extends AbstractMapController {
	
	private final String universiteCategoryId;
		
	public UniversityMapController(String jsonUrl, String universiteCategoryId, String restaurationUniversitaireCategoryId) {
		super(jsonUrl, restaurationUniversitaireCategoryId);
		this.universiteCategoryId = universiteCategoryId;

	}

	@Override
	protected String provideViewName() {
		return "university-map.jsp";
	}

	@Override
	protected Poi[] providePois() {

		RestTemplate template = restTemplate();
		PoiEmbedded poiContainer = template.getForObject(jsonUrl + "/pois/search/findByUniversityAndCategoryRoot?universityId=" + getUniversity().getId()+"&categoryId=" + universiteCategoryId + "&size=200", PoiEmbedded.class);
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

}
