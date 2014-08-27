package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;
import fr.univmobile.web.commons.AbstractController;
import fr.univmobile.web.commons.BuildInfoUtils;
import fr.univmobile.web.commons.ControllerException;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "about", "about/" })
public class AboutController extends AbstractController {

	public AboutController(final String jsonURL) {

		this.jsonURL = checkNotNull(jsonURL, "jsonURL");
	}

	private final String jsonURL;

	@Override
	public View action() throws ControllerException {

		setAttribute("buildInfo",
				BuildInfoUtils.loadBuildInfo(getServletContext()));

		setAttribute("jsonURL", jsonURL);

		return new View("about.jsp");
	}
}
