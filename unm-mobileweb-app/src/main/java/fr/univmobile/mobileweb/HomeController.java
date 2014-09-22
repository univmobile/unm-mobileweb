package fr.univmobile.mobileweb;

import java.io.IOException;

import fr.univmobile.backend.client.AppToken;
import fr.univmobile.web.commons.AbstractJspController;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "" })
public class HomeController extends AbstractJspController {

	@Override
	public View action() throws IOException {

		if (hasSessionAttribute("appToken")) {

			final AppToken appToken = getSessionAttribute("appToken",
					AppToken.class);

			setAttribute("user", appToken.getUser());
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
}
