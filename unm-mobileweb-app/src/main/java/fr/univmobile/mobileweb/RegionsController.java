package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;
import static fr.univmobile.mobileweb.RegionsUtils.getRegionById;

import java.io.IOException;

import fr.univmobile.backend.client.Region;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.University;
import fr.univmobile.web.commons.AbstractJspController;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "regions", "regions/" })
public class RegionsController extends AbstractJspController {

	public RegionsController(final RegionClient regions) {

		this.regions = checkNotNull(regions, "regions");
	}

	private final RegionClient regions;

	@Override
	public View action() throws IOException {

		final Region[] r = regions.getRegions();

		setAttribute("regions", r);

		final SelectRegion selectRegion = getHttpInputs(SelectRegion.class);

		if (selectRegion.isHttpValid()) {

			final Region region = getRegionById(regions, selectRegion.region());

			if (region != null) {

				setAttribute("region", region);

				final University[] universities = //
				regions.getUniversitiesByRegion(region.getId());

				setAttribute("universities", universities);

				return new View("universities.jsp");
			}
		}

		final ShowSelectedRegion showSelectedRegion = getHttpInputs(ShowSelectedRegion.class);

		if (showSelectedRegion.isHttpValid()) {

			setAttribute("selectedRegion", showSelectedRegion.selected());
		}

		return new View("regions.jsp");
	}

	@HttpMethods("GET")
	private interface SelectRegion extends HttpInputs {

		@HttpRequired
		@HttpParameter(trim = true)
		String region();
	}

	@HttpMethods("GET")
	private interface ShowSelectedRegion extends HttpInputs {

		@HttpRequired
		@HttpParameter(trim = true)
		String selected();
	}
}
