package fr.univmobile.backend.client.http;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avcompris.lang.NotImplementedException;

import fr.univmobile.backend.client.PoiClient;
import fr.univmobile.backend.client.json.PoiJSONClient;

public class PoiJSONHttpClient extends AbstractJSONHttpClient implements
		PoiJSONClient {

	protected PoiJSONHttpClient(final String url, final PoiClient client) {

		super(url);

		throw new NotImplementedException();
	}

	// private final PoiClient client;

	@Inject
	public PoiJSONHttpClient(@Named("PoiJSONHttpClient")//
			final String url) {

		super(url);

		if (log.isInfoEnabled()) {
			log.info("<init>.url: " + url);
		}

		if (!url.endsWith("/json/pois") && !url.endsWith("/json/pois.json")) {
			log.warn("<init>.url doesn't have the conventional /json/pois[.json] termination: "
					+ url);
		}

		// this.client = new PoiClientFromJSON(this);
	}

	private static final Log log = LogFactory.getLog(PoiJSONHttpClient.class);

	@Override
	public String getPoisJSON() throws IOException {

		log.info("getPoisJSON()...");

		if (log.isDebugEnabled()) {
			log.debug("url: " + url);
		}

		return wget(url);
	}
}