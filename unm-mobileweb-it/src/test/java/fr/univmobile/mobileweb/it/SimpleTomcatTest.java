package fr.univmobile.mobileweb.it;

import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class SimpleTomcatTest {

	@Test
	public void testTomcatSimple() throws Exception {

		final String content = wget("http://localhost:8380/unm-mobileweb/",
				UTF_8);

		assertTrue(content.contains("<h1>UnivMobile</h1>"));
	}

	private static String wget(final String url, final String encoding)
			throws IOException {

		return new String(wget(url), encoding);
	}

	private static byte[] wget(final String url) throws IOException {

		final HttpClient client = new HttpClient();

		final HttpMethod method = new GetMethod(url);

		client.executeMethod(method);

		final int statusCode = method.getStatusCode();

		if (statusCode != 200) {
			
			throw new IOException("HTTP " + statusCode + ": " + url);
		}

		final InputStream is = method.getResponseBodyAsStream();
		try {

			return IOUtils.toByteArray(is);

		} finally {
			is.close();
		}
	}
}
