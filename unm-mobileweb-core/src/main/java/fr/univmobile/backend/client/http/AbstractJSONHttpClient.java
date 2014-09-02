package fr.univmobile.backend.client.http;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	protected static String wget(final String url) throws IOException {

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
}
