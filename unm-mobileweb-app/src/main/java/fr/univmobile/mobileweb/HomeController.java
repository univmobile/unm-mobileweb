package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;
import static fr.univmobile.backend.client.RegionsUtils.getUniversityById;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.univmobile.backend.client.AppToken;
import fr.univmobile.backend.client.ClientException;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.SessionClient;
import fr.univmobile.backend.client.University;
import fr.univmobile.web.commons.AbstractJspController;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "" })
public class HomeController extends AbstractJspController {

	public HomeController(final String apiKey,
			final SessionClient sessionClient, final RegionClient regions) {

		this.apiKey = checkNotNull(apiKey, "apiKey");
		this.sessionClient = checkNotNull(sessionClient, "sessionClient");
		this.regions = checkNotNull(regions, "regions");
	}

	private final String apiKey;
	private final SessionClient sessionClient;
	private final RegionClient regions;

	private static final Log log = LogFactory.getLog(HomeController.class);

	@Override
	public View action() throws IOException {

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

		final SelectedUniversity selected = getHttpInputs(SelectedUniversity.class);

		if (selected.isHttpValid()) {

			final String univ = selected.univ();

			final University university = getUniversityById(regions, univ);

			if (university != null) {

				setSessionAttribute("univ", univ);
			}
		}

		if (hasSessionAttribute("univ")) {

			final String univ = getSessionAttribute("univ", String.class);

			final University university = getUniversityById(regions, univ);

			if (university != null) {

				setAttribute("selectedUniversityId", university.getId());
				setAttribute("selectedUniversityLabel", university.getTitle());
			}
		}

		return new View("home.jsp");
	}

	@HttpMethods("GET")
	private interface SelectedUniversity extends HttpInputs {

		@HttpRequired
		@HttpParameter(trim = true)
		String region();

		@HttpRequired
		@HttpParameter(trim = true)
		String univ();
	}

	@HttpMethods({ "GET", "POST" })
	private interface Logout extends HttpInputs {

		@HttpRequired
		@HttpParameter
		String logout();
	}
}
