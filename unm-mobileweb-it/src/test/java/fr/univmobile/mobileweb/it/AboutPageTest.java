package fr.univmobile.mobileweb.it;

import static fr.univmobile.backend.core.impl.ConnectionType.MYSQL;
import static fr.univmobile.testutil.PropertiesUtils.getSettingsTestRefProperty;
import static fr.univmobile.testutil.PropertiesUtils.getTestProperty;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Before;
import org.junit.Test;

import fr.univmobile.backend.it.TestBackend;
import fr.univmobile.it.commons.AppiumSafariEnabledTest;

public class AboutPageTest extends AppiumSafariEnabledTest {

	@Before
	public void setUpData() throws Exception {

		// "/tmp/unm-mobileweb/dataDir"
		final String dataDir = TestBackend.readBackendAppDataDir(new File(
				"target", "unm-backend-app/WEB-INF/web.xml"));

		final Connection cxn = DriverManager.getConnection(
				getTestProperty("mysql.url"),
				getTestProperty("mysql.username"),
				getSettingsTestRefProperty("mysql.password.ref"));
		try {

			TestBackend.setUpData("001", new File(dataDir), MYSQL, cxn);

		} finally {
			cxn.close();
		}
	}

	@Test
	public void testAboutPage_display() throws Exception {

		get("http://localhost:8380/unm-mobileweb/about/");

		waitForElementById(60, "div-info");

		takeScreenshot("about.png");

		savePageSource("about.html");
	}
}
