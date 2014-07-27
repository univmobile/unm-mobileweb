package fr.univmobile.mobileweb;

import java.io.IOException;

import fr.univmobile.web.commons.AbstractController;
import fr.univmobile.web.commons.BuildInfoUtils;
import fr.univmobile.web.commons.Paths;

@Paths({ "about", "about/" })
public class AboutController extends AbstractController {

	@Override
	public String action() throws IOException {

		setAttribute("buildInfo",
				BuildInfoUtils.loadBuildInfo(getServletContext()));

		return "about.jsp";
	}
}
