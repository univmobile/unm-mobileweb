package fr.univmobile.backend.client.http;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.univmobile.backend.client.json.SessionJSONClient;

public class SessionJSONHttpClient extends AbstractJSONHttpClient implements
		SessionJSONClient {

	@Inject
	public SessionJSONHttpClient(@Named("SessionJSONHttpClient")//
			final String url) {

		super(url);

		if (log.isInfoEnabled()) {
			log.info("<init>.url: " + url);
		}

		if (!url.endsWith("/json/session")
				&& !url.endsWith("/json/session.json")) {
			log.warn("<init>.url doesn't have the conventional /json/session[.json] termination: "
					+ url);
		}
	}

	private static final Log log = LogFactory
			.getLog(SessionJSONHttpClient.class);

	@Override
	public String loginJSON(final String apiKey, final String login,
			final String password) throws IOException {

		if (log.isDebugEnabled()) {
			log.info("loginJSON():" + login + "...");
		}

		if (log.isDebugEnabled()) {
			log.debug("url: " + url);
		}

		return wpost(url,//
				"apiKey", apiKey, //
				"login", login, //
				"password", password);
	}

	@Override
	public String getAppTokenJSON(final String apiKey, final String appTokenId)
			throws IOException {

		if (log.isDebugEnabled()) {
			log.info("getAppTokenJSON():" + appTokenId + "...");
		}

		if (log.isDebugEnabled()) {
			log.debug("url: " + url);
		}

		return wpost(url,//
				"apiKey", apiKey, //
				"appTokenId", appTokenId);
	}

	@Override
	public String logoutJSON(final String apiKey, final String appTokenId)
			throws IOException {

		if (log.isDebugEnabled()) {
			log.info("logoutJSON():" + appTokenId + "...");
		}

		return wpost(url,//
				"apiKey", apiKey, //
				"appTokenId", appTokenId, //
				"logout", "" // No value for "logout" HTTP parameter
		);
	}

	@Override
	public String prepareJSON(final String apiKey)
			throws IOException {

		if (log.isDebugEnabled()) {
			log.info("prepareJSON():" + apiKey+ "...");
		}

		return wpost(url,//
				"apiKey", apiKey, //
				"prepare", "" // No value for "logout" HTTP parameter
		);
	}
}
