package fr.univmobile.backend.client.http;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.univmobile.commons.http.HttpUtils;

abstract class AbstractJSONHttpClient {

	protected AbstractJSONHttpClient(final String url) {

		this.url = checkNotNull(url, "url");

		if (log.isInfoEnabled()) {
			log.info("<init>.url: " + url);
		}
	}

	protected final String url;

	private static final Log log = LogFactory
			.getLog(AbstractJSONHttpClient.class);
	
	protected static String wget(final String url)
			throws IOException {
		
		return HttpUtils.wget(url);
	}
	
	protected static String wpost(final String url, final String... params)
			throws IOException {
		
		return HttpUtils.wpost(url,params);
	}
}
