package fr.univmobile.mobileweb.it;

import static fr.univmobile.backend.core.impl.ConnectionType.MYSQL;
import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import fr.univmobile.backend.it.TestBackend;
import fr.univmobile.testutil.PropertiesUtils;

public class SimpleTomcatTest {

	@Before
	public void setUp() throws Exception {

		// "http://localhost:8380/unm-backend/"
		baseURL = TestBackend.readMobilewebAppBaseURL(new File("target",
				"unm-mobileweb-app-local/WEB-INF/web.xml"));

		// "/tmp/unm-mobileweb/dataDir"
		final String dataDir = TestBackend
				.readMobilewebAppLocalDataDir(new File("target",
						"unm-mobileweb-app-local/WEB-INF/web.xml"));

		final Connection cxn = DriverManager.getConnection(
				PropertiesUtils.getTestProperty("mysqlUrl"),
				PropertiesUtils.getTestProperty("mysqlUsername"),
				PropertiesUtils.getTestProperty("mysqlPassword"));
		try {

			TestBackend.setUpData("001", new File(dataDir), MYSQL, cxn);

		} finally {
			cxn.close();
		}

		final String logFile = TestBackend.readLog4jLogFile(new File("target",
				"unm-mobileweb-app-local/WEB-INF/classes/log4j.xml"));

		System.out.println("Log file: " + logFile);
	}

	private String baseURL;

	@Test
	public void testTomcatSimple() throws Exception {

		// "http://localhost:8380/unm-mobileweb/"
		final String content = wget(baseURL, UTF_8);

		assertTrue(content.contains(">UnivMobile</h1>"));
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
