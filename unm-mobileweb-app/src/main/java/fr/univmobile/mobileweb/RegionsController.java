package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javax.annotation.Nullable;

import fr.univmobile.backend.client.Region;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.University;
import fr.univmobile.web.commons.AbstractController;
import fr.univmobile.web.commons.ControllerException;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "regions", "regions/" })
public class RegionsController extends AbstractController {

	public RegionsController(final RegionClient regions) {

		this.regions = checkNotNull(regions, "regions");
	}

	private final RegionClient regions;

	@Nullable
	private Region getRegionById(final String id) throws IOException {

		checkNotNull(id, "id");

		for (final Region region : regions.getRegions()) {

			if (id.equals(region.getId())) {

				return region;
			}
		}

		return null;
	}

	@Override
	public View action() throws ControllerException {

		try {

			return actionWithIOException();

		} catch (final IOException e) {
			throw new ControllerException(e);
		}
	}

	private View actionWithIOException() throws IOException {

		final Region[] r = regions.getRegions();

		setAttribute("regions", r);

		final SelectRegion selectRegion = getHttpInputs(SelectRegion.class);

		if (selectRegion.isHttpValid()) {

			final Region region = getRegionById(selectRegion.region());

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
