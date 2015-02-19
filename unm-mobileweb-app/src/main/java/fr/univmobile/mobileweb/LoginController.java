package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;
import static fr.univmobile.backend.client.RegionsUtils.getUniversityById;

import java.io.IOException;

import fr.univmobile.backend.client.AppToken;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.University;
import fr.univmobile.web.commons.AbstractJspController;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "login", "login/"/*, "profile", "profile/" */ })
public class LoginController extends AbstractJspController {

	public LoginController(final RegionClient regions) {

		this.regions = checkNotNull(regions, "regions");
	}

	private final RegionClient regions;

	@Override
	public View action() throws IOException {

		// If a user is already connected, display his or her details page

		if (hasSessionAttribute("appToken")) {

			final AppToken appToken = getSessionAttribute("appToken",
					AppToken.class);

			setAttribute("user", appToken.getUser());

			return new View("profile.jsp");
		}

		// Otherwise, display the login page

		final SelectedUniversity selected = getHttpInputs(SelectedUniversity.class);

		final University university = selected.isHttpValid() ? getUniversityById(
				regions, selected.univ()) : null;

		if (university != null) {

			setAttribute("selectedUniversityId", university.getId());
			setAttribute("selectedUniversityLabel", university.getTitle());

			final String shibbolethIdentityProvider = university
					.getShibbolethIdentityProvider();

			if (shibbolethIdentityProvider != null) {

				setAttribute("shibbolethEnabled", true);
			}
		}

		return new View("login.jsp");
	}

	@HttpMethods("GET")
	private interface SelectedUniversity extends HttpInputs {

		// @HttpRequired
		// @HttpParameter(trim = true)
		// String region();

		@HttpRequired
		@HttpParameter(trim = true)
		String univ();
	}
}
