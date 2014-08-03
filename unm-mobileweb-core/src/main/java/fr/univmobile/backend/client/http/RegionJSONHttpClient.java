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

import fr.univmobile.backend.client.json.RegionJSONClient;

public class RegionJSONHttpClient implements RegionJSONClient {

	@Inject
	public RegionJSONHttpClient(final String url) {

		this.url = checkNotNull(url, "url");
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

	@Override
	public String getUniversitiesJSONByRegion(final String regionId)
			throws IOException {

		throw new NotImplementedException();
	}
}
