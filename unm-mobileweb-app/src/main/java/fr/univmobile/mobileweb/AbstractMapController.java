package fr.univmobile.mobileweb;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

public abstract class AbstractMapController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;
	protected final String restaurationUniversitaireCategoryId;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public AbstractMapController(final String jsonUrl, final String restaurationUniversitaireCategoryId) {

		this.jsonUrl = jsonUrl;
		this.restaurationUniversitaireCategoryId = restaurationUniversitaireCategoryId;
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
		
		if (!hasSessionAttribute("univ")) {

			sendRedirect(getBaseURL());
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
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			
			//map attributes
			setAttribute("API_KEY", "AIzaSyC8uD1y1Dgx0W6JJMCQm7V1OJx_nsbRmBE");
			setAttribute("mapHeight", "600px");
			setAttribute("restaurationUniversitaireCategoryId", restaurationUniversitaireCategoryId);
			
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

	/*
	@RequestMapping(value="getComments",method=RequestMethod.GET)
	public @ResponseBody String getComments() {
		System.out.println("getComments");
		return "whatever";
	}
	*/
}



	
























/* old code 1
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.univmobile.backend.client.Poi;
import fr.univmobile.backend.client.PoiClient;
import fr.univmobile.backend.client.PoiGroup;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.web.commons.AbstractJspController;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "geocampus", "geocampus/" })
public class GeocampusController extends AbstractJspController {

	public GeocampusController(final RegionClient regions, final PoiClient pois) {

		// this.regions =
		checkNotNull(regions, "regions");
		this.pois = checkNotNull(pois, "pois");
	}

	// private final RegionClient regions;
	private final PoiClient pois;

	@Override
	public View action() throws IOException {

		setAttribute("mode", "list");

		setAttribute("map", new Map("48.84650925911,2.3459243774", 13));

		final List<Pois> list = new ArrayList<Pois>();

		setAttribute("pois", list);

		for (final PoiGroup poiGroup : pois.getPois().getGroups()) {

			list.add(new Pois(poiGroup));
		} 
*/ //end of old code 1

		/*
		 * baseURL: ${baseURL}
		 * 
		 * pois: type: fr.univmobile.model.PoiCategory[] value: - name:
		 * "Région : Bretagne" pois: type: fr.univmobile.model.Poi[] value: -
		 * id: 1 name: Université Panthéon-Sorbonne - Paris I address: 12 place
		 * du Panthéon 75231 PARIS phone: 0144078000 floor: null email: null
		 * fax: null openingHours: null itinerary: null coordinates:
		 * 48.84650925911,2.3459243774 latitude: 48.84650925911 longitude:
		 * 2.3459243774 url: Http://www.univ-paris1.fr/ logo:
		 * http://admin.univmobile.fr/images/universities/logos/univ_paris1.jpg
		 * image: null imageWidth: 200 imageHeight: 100 markerType: green
		 * markerIndex: A - id: 931
		 */
		/*
		 * final Region[] r = regions.getRegions();
		 * 
		 * setAttribute("regions", r);
		 * 
		 * final SelectRegion selectRegion = getHttpInputs(SelectRegion.class);
		 * 
		 * if (selectRegion.isHttpValid()) {
		 * 
		 * final Region region = getRegionById(selectRegion.region());
		 * 
		 * if (region != null) {
		 * 
		 * setAttribute("region", region);
		 * 
		 * final University[] universities = //
		 * regions.getUniversitiesByRegion(region.getId());
		 * 
		 * setAttribute("universities", universities);
		 * 
		 * return "universities.jsp"; } }
		 * 
		 * final ShowSelectedRegion showSelectedRegion =
		 * getHttpInputs(ShowSelectedRegion.class);
		 * 
		 * if (showSelectedRegion.isHttpValid()) {
		 * 
		 * setAttribute("selectedRegion", showSelectedRegion.selected()); }
		 */

/* old code 2
		return new View("geocampus_pois.jsp");
	}

	public static class Pois {

		private Pois(final PoiGroup poiGroup) {

			this.poiGroup = poiGroup;
		}

		private final PoiGroup poiGroup;

		public String getName() {

			return poiGroup.getGroupLabel();
		}

		public Poi[] getPois() {

			return poiGroup.getPois();
		}
	}

	public static class Map {

		private Map(final String center, final int zoom) {

			this.center = center;
			this.zoom = zoom;
		}

		public String getCenter() {

			return center;
		}

		public int getZoom() {

			return zoom;
		}

		private final String center;
		private final int zoom;
	}
}
*/ //end of old code 2
