package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.web.commons.AbstractController;
import fr.univmobile.web.commons.BuildInfoUtils;
import fr.univmobile.web.commons.Paths;

@Paths({ "about", "about/" })
public class AboutController extends AbstractController {

	public AboutController(final RegionClient regions) {
		
		this.regions = checkNotNull(regions,"regions");		
	}
	
	private final RegionClient regions;
	
	@Override
	public String action() throws IOException {

		setAttribute("buildInfo",
				BuildInfoUtils.loadBuildInfo(getServletContext()));

		return "about.jsp";
	}
}
