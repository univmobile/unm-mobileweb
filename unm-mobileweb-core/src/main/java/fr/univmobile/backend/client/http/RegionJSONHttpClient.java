package fr.univmobile.backend.client.http;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.univmobile.backend.client.Region;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.RegionClientFromJSON;
import fr.univmobile.backend.client.json.RegionJSONClient;

public class RegionJSONHttpClient extends AbstractJSONHttpClient implements
		RegionJSONClient {

	private final RegionClient client;

	@Inject
	public RegionJSONHttpClient(@Named("RegionJSONHttpClient")//
			final String url) {

		super(url);

		if (log.isInfoEnabled()) {
			log.info("<init>.url: " + url);
		}

		if (!url.endsWith("/json/regions")
				&& !url.endsWith("/json/regions.json")) {
			log.warn("<init>.url doesn't have the conventional /json/region[.json] termination: "
					+ url);
		}

		this.client = new RegionClientFromJSON(this);
	}

	private static final Log log = LogFactory
			.getLog(RegionJSONHttpClient.class);

	@Override
	public String getRegionsJSON() throws IOException {

		log.info("getRegionsJSON()...");

		if (log.isDebugEnabled()) {
			log.debug("url: " + url);
		}

		return wget(url);
	}

	private Region getRegionById(final String regionId) throws IOException {

		checkNotNull(regionId, "regionId");

		final Region[] regions = client.getRegions();

		for (final Region region : regions) {

			if (regionId.equals(region.getId())) {

				return region;
			}
		}

		throw new IllegalArgumentException("Unknown regionId: " + regionId);
	}

	@Override
	public String getUniversitiesJSONByRegion(final String regionId)
			throws IOException {

		if (log.isInfoEnabled()) {
			log.info("getUniversitiesJSONByRegion():" + regionId + "...");
		}

		final Region region = this.getRegionById(regionId);

		final String url = region.getUrl();

		return wget(url);
	}
}
