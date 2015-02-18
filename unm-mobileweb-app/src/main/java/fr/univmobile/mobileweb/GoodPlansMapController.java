package fr.univmobile.mobileweb;

import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.Category;
import fr.univmobile.mobileweb.models.CategoryEmbedded;
import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.PoiEmbedded;
import fr.univmobile.web.commons.Paths;

@Paths({ "goodplans-map" })
public class GoodPlansMapController extends AbstractMapController {
	
	private final String bonPlansCategoryId;
		
	public GoodPlansMapController(String jsonUrl, String bonPlansCategoryId, String restaurationUniversitaireCategoryId) {
		super(jsonUrl, restaurationUniversitaireCategoryId);
		this.bonPlansCategoryId = bonPlansCategoryId;

	}

	@Override
	protected String provideViewName() {
		return "goodplans-map.jsp";
	}

	@Override
	protected Poi[] providePois() {

		RestTemplate template = restTemplate();
		PoiEmbedded poiContainer = template.getForObject(jsonUrl + "/pois/search/findByUniversityAndCategoryRoot?universityId=" + getUniversity().getId()+"&categoryId=" + bonPlansCategoryId + "&size=200", PoiEmbedded.class);
		if (poiContainer._embedded != null) {	
			return poiContainer._embedded.getPois();
		} else {
			return null;
		}
	}

	@Override
	protected Category[] provideCategories() {
		
		RestTemplate template = restTemplate();
		CategoryEmbedded categoryContainer = template.getForObject(jsonUrl + "/categories/"+ bonPlansCategoryId +"/children", CategoryEmbedded.class);
		if (categoryContainer._embedded != null) {		
			return categoryContainer._embedded.getCategories();
		} else {
			return null;
		}
	}

}
