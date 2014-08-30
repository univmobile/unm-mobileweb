package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import fr.univmobile.backend.client.HomeClient;
import fr.univmobile.web.commons.AbstractController;
import fr.univmobile.web.commons.BuildInfoUtils;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "about", "about/" })
public class AboutController extends AbstractController {

	public AboutController(final String jsonURL, final HomeClient homeClient) {

		this.jsonURL = checkNotNull(jsonURL, "jsonURL");
		this.homeClient = checkNotNull(homeClient, "homeClient");
	}

	private final String jsonURL;
	private final HomeClient homeClient;

	@Override
	public View action() throws IOException {

		setAttribute("buildInfo",
				BuildInfoUtils.loadBuildInfo(getServletContext()));

		setAttribute("jsonURL", jsonURL);

		String jsonBaseURL = null;

		try {

			jsonBaseURL = homeClient.getHome().getUrl();

		} catch (final Exception e) {

			jsonBaseURL = e.toString();
		}

		setAttribute("jsonBaseURL", jsonBaseURL);

		return new View("about.jsp");
	}

}
