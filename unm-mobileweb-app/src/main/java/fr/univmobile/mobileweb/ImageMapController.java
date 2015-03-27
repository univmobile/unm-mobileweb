package fr.univmobile.mobileweb;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.univmobile.mobileweb.models.ImageMap;
import fr.univmobile.mobileweb.models.Poi;
import fr.univmobile.mobileweb.models.PoiEmbedded;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "image-map" })
public class ImageMapController extends AsbtractMobileWebJspController {

	protected final String jsonUrl;
	protected final String imagesMapsBaseUrl = "http://univmobile-dev.univ-paris1.fr/testSP/files/imagemaps/";
	
	private static final Log log = LogFactory
			.getLog(ImageMapController.class);
	
	public ImageMapController(String jsonUrl) {
		this.jsonUrl = jsonUrl;
	}
	
	@Override
	public View action() throws Exception {
		if (!hasSessionAttribute("univ")) {		

			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {
			MapRequested mapRequested = getHttpInputs(MapRequested.class);
			if (mapRequested.isHttpValid()) {
				Long mapId = Long.parseLong(mapRequested.im());
				Long selectedPoiId = Long.parseLong(mapRequested.poi());
				
				// Get the information of the map :
				ImageMap map = restTemplate().getForObject(jsonUrl + "/imageMaps/" + String.valueOf(mapId), ImageMap.class);
				
				// Get the list of Pois :
				PoiEmbedded embedded = restTemplate().getForObject(jsonUrl + "/imageMaps/" + String.valueOf(mapId) + "/pois", PoiEmbedded.class);
				map.setPois(Arrays.asList(embedded._embedded.getPois()));
				for (Poi poi : map.getPois()) {
					if (poi.getId() == selectedPoiId) {
						map.setSelectedPoi(poi);
					}
				}
				
				map.setUrl(imagesMapsBaseUrl + map.getUrl());
				URL imageUrl = new URL(map.getUrl());
				BufferedImage bimg = ImageIO.read(imageUrl);
				map.setWidth(bimg.getWidth());
				map.setHeight(bimg.getHeight());
				
				//menu attributes
				setAttribute("universityLogo", getUniversityLogo());
				setAttribute("university", getUniversity());
				setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
				setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
				setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
				setAttribute("currentAbsolutePath", getAbsolutePath());
				
				//map attributes
				setAttribute("API_KEY", "AIzaSyBLdHFXnqofrZMnGb5W5NDnQKK7amivZS4");
				
				log.info("Number of POI on image of id " + mapId + " : " + map.getPois().size());
				setAttribute("imageMap", map);
				setAttribute("isIDF", getUniversity().getRegionId() == 1);
				return new View("image-map.jsp");
			}
		}
		sendRedirect(getBaseURL()+"/");
		return null;
	}
	
	@HttpMethods("GET")
	private interface MapRequested extends HttpInputs {
		
		@HttpRequired
		@HttpParameter(trim = true)
		String im();

		@HttpParameter(trim = true)
		String poi();
	}
	
}
