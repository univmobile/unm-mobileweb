package fr.univmobile.backend.client.http;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avcompris.lang.NotImplementedException;

import fr.univmobile.backend.client.Region;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.RegionClientFromJSON;
import fr.univmobile.backend.client.json.RegionJSONClient;

public class RegionJSONHttpClient implements RegionJSONClient {

	protected RegionJSONHttpClient(final String url, final RegionClient client) {

		throw new NotImplementedException();
	}

	private final RegionClient client;

	@Inject
	public RegionJSONHttpClient(final String url) {

		this.url = checkNotNull(url, "url");

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

	private final String url;

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

	private static String wget(final String url) throws IOException {

		final URL u = new URL(url);

		final HttpURLConnection cxn = (HttpURLConnection) u.openConnection();
		try {

			log.debug("openConnection() OK");

			final InputStream is = new BufferedInputStream(cxn.getInputStream());
			try {

				log.debug("getInputStream() OK");

				return IOUtils.toString(is, UTF_8);

			} finally {

				log.debug("is.close()...");

				is.close();
			}

		} finally {

			log.debug("cxn.disconnect()...");

			cxn.disconnect();
		}
	}

	public Region getRegionById(final String regionId) throws IOException {

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
