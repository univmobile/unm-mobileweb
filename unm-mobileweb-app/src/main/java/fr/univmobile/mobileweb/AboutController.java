package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import fr.univmobile.web.commons.AbstractController;
import fr.univmobile.web.commons.BuildInfoUtils;
import fr.univmobile.web.commons.Paths;

@Paths({ "about", "about/" })
public class AboutController extends AbstractController {

	public AboutController(final String jsonURL) {

		this.jsonURL = checkNotNull(jsonURL, "jsonURL");
	}

	private final String jsonURL;

	@Override
	public String action() throws IOException {

		setAttribute("buildInfo",
				BuildInfoUtils.loadBuildInfo(getServletContext()));

		setAttribute("jsonURL", jsonURL);

		return "about.jsp";
	}
}
