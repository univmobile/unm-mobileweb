package fr.univmobile.mobileweb.it;

import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import fr.univmobile.backend.it.TestBackend;

public class SimpleTomcatTest {

	@Before
	public void setUp() throws Exception {

		// "http://localhost:8380/unm-backend/"
		baseURL = TestBackend.readMobilewebAppBaseURL(new File("target",
				"unm-mobileweb-app/WEB-INF/web.xml"));

		// "/tmp/unm-mobileweb/dataDir"
		final String dataDir = TestBackend.readBackendAppDataDir(new File(
				"target", "unm-backend-app/WEB-INF/web.xml"));

		TestBackend.setUpData("001", new File(dataDir));

		final String logFile = TestBackend.readLog4jLogFile(new File("target",
				"unm-mobileweb-app/WEB-INF/classes/log4j.xml"));

		System.out.println("Log file: " + logFile);

		final String backendLogFile = TestBackend.readLog4jLogFile(new File(
				"target", "unm-backend-app/WEB-INF/classes/log4j.xml"));

		System.out.println("Backend Log file: " + backendLogFile);
	}

	private String baseURL;

	@Test
	public void testTomcatSimple() throws Exception {

		final String content = wget(baseURL, UTF_8);

		assertTrue(content.contains(">UnivMobile</h1>"));
	}

	@Test
	public void testTomcatSimpleBackend() throws Exception {

		final String content = wget(
				"http://localhost:8380/unm-backend/json/regions", UTF_8);

		assertTrue(content.contains("\"Bretagne\""));
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
