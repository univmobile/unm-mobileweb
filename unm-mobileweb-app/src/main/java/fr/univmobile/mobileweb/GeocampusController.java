package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.univmobile.backend.client.Poi;
import fr.univmobile.backend.client.PoiClient;
import fr.univmobile.backend.client.PoiGroup;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.web.commons.AbstractController;
import fr.univmobile.web.commons.ControllerException;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "geocampus", "geocampus/" })
public class GeocampusController extends AbstractController {

	public GeocampusController(final RegionClient regions, final PoiClient pois) {

		// this.regions =
		checkNotNull(regions, "regions");
		this.pois = checkNotNull(pois, "pois");
	}

	// private final RegionClient regions;
	private final PoiClient pois;

	@Override
	public View action() throws ControllerException {

		setAttribute("mode", "list");

		setAttribute("map", new Map("48.84650925911,2.3459243774", 13));

		final List<Pois> list = new ArrayList<Pois>();

		setAttribute("pois", list);

		final PoiGroup[] poiArray;

		try {

			poiArray = pois.getPois();

		} catch (final IOException e) {
			throw new ControllerException(e);
		}

		for (final PoiGroup poiGroup : poiArray) {

			list.add(new Pois(poiGroup));
		}

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
