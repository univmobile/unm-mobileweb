package fr.univmobile.mobileweb;

import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.Category;
import fr.univmobile.mobileweb.models.CategoryEmbedded;
import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.PoiEmbedded;
import fr.univmobile.web.commons.Paths;

@Paths({ "paris-map" })
public class ParisMapController extends AbstractMapController {
	
	private final String parisCategoryId;
		
	public ParisMapController(String jsonUrl, String parisCategoryId, String restaurationUniversitaireCategoryId) {
		super(jsonUrl, restaurationUniversitaireCategoryId);
		this.parisCategoryId = parisCategoryId;

	}

	@Override
	protected String provideViewName() {
		return "paris-map.jsp";
	}

	@Override
	protected Poi[] providePois() {

		RestTemplate template = restTemplate();
		PoiEmbedded poiContainer = template.getForObject(jsonUrl + "/pois/search/findByCategoryRoot?categoryId=" + parisCategoryId + "&size=250", PoiEmbedded.class);
		if (poiContainer._embedded != null) {	
			return poiContainer._embedded.getPois();
		} else {
			return null;
		}
	}

	@Override
	protected Category[] provideCategories() {
		
		RestTemplate template = restTemplate();
		CategoryEmbedded categoryContainer = template.getForObject(jsonUrl + "/categories/"+ parisCategoryId +"/children", CategoryEmbedded.class);
		if (categoryContainer._embedded != null) {		
			return categoryContainer._embedded.getCategories();
		} else {
			return null;
		}
	}
	
	@Override
	protected String getCategoryRootId() {
		return parisCategoryId;
	}

}
