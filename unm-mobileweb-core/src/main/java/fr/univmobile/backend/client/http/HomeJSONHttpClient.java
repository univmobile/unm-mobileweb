package fr.univmobile.backend.client.http;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.univmobile.backend.client.json.HomeJSONClient;

public class HomeJSONHttpClient extends AbstractJSONHttpClient implements
		HomeJSONClient {

	@Inject
	public HomeJSONHttpClient(@Named("HomeJSONHttpClient")//
			final String url) {

		super(url);

		if (log.isInfoEnabled()) {
			log.info("<init>.url: " + url);
		}

		if (!url.endsWith("/json") && !url.endsWith("/json/")
				&& !url.endsWith("/json.json")) {
			log.warn("<init>.url doesn't have the conventional /json[.json] termination: "
					+ url);
		}
	}

	private static final Log log = LogFactory.getLog(HomeJSONHttpClient.class);

	@Override
	public String getHomeJSON() throws IOException {

		log.info("getHomeJSON()...");

		if (log.isDebugEnabled()) {
			log.debug("url: " + url);
		}

		return wget(url);
	}
}
