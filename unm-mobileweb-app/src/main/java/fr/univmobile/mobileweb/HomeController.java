package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;
import static fr.univmobile.backend.client.RegionsUtils.getUniversityById;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.univmobile.backend.client.AppToken;
import fr.univmobile.backend.client.ClientException;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.SessionClient;
import fr.univmobile.backend.client.University;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "" })
public class HomeController extends AsbtractMobileWebJspController {

	public HomeController(final String jsonUrl, final String apiKey,
			final SessionClient sessionClient, final RegionClient regions) {

		this.jsonUrl = jsonUrl;
		this.apiKey = checkNotNull(apiKey, "apiKey");
		this.sessionClient = checkNotNull(sessionClient, "sessionClient");
		this.regions = checkNotNull(regions, "regions");
	}

	private final String jsonUrl;
	private final String apiKey;
	private final SessionClient sessionClient;
	private final RegionClient regions;
	
	private static final Log log = LogFactory.getLog(HomeController.class);

	@Override
	public View action() throws IOException {

		/*
		if (hasSessionAttribute("appToken")) {

			final AppToken appToken = getSessionAttribute("appToken",
					AppToken.class);

			final String appTokenId = appToken.getId();

			if (getHttpInputs(Logout.class).isHttpValid()) {

				if (log.isInfoEnabled()) {
					log.info("Logging out: " + appTokenId);
				}

				try {

					sessionClient.logout(apiKey, appTokenId);

				} catch (final ClientException e) {

					log.error(e);
				}

				removeSessionAttribute("appToken");

			} else {

				setAttribute("user", appToken.getUser());
			}
		}
		*/

		final SelectedUniversity selected = getHttpInputs(SelectedUniversity.class);

		if (selected.isHttpValid()) {

			final String univ = selected.univ();

		/*	final University university = getUniversityById(regions, univ);

			if (university != null) {

				setSessionAttribute("univ", univ);
			}*/
		}

		if (hasSessionAttribute("univ")) {

			final String univ = getSessionAttribute("univ", String.class);

			final University university = getUniversityById(regions, univ);

			if (university != null) {

				setAttribute("selectedUniversityId", university.getId());
				setAttribute("selectedUniversityLabel", university.getTitle());
			}
		}
		
		
		// Get the list of region
		RestTemplate template = restTemplate();
		
		RegionEmbedded regionContainer = template.getForObject("http://vps111534.ovh.net:8082/regions", RegionEmbedded.class);
		setAttribute("regionsList", regionContainer._embedded.getRegions());
		
		int i = 0;
		for (Region region : regionContainer._embedded.getRegions()) {
			i++;
			UniversityEmbedded universityContainer = template.getForObject(jsonUrl + "/regions/" + i/*region.getId()*/ + "/universities", UniversityEmbedded.class);
			region.setUniversities(universityContainer._embedded.getUniversities());
		}
		
		//log.debug("Region size : " + regionContainer._embedded.getRegions().length);
		
		return new View("home.jsp");
	}
	
	@HttpMethods("GET")
	private interface SelectedUniversity extends HttpInputs {


		@HttpRequired
		@HttpParameter(trim = true)
		String univ();
	}

/*	@HttpMethods({ "GET", "POST" })
	private interface Logout extends HttpInputs {

		@HttpRequired
		@HttpParameter
		String logout();
	}*/
	
	/*public class RegionEmbedded {

		@JsonProperty("_embedded")
		public RegionList _embedded;
	}
	
	public class RegionList {

		private Region[] regions;

		public Region[] getRegions() {
			return regions;
		}
		
	}*/
}
