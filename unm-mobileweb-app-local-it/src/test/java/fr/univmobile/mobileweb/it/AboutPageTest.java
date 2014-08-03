package fr.univmobile.mobileweb.it;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import fr.univmobile.backend.it.TestBackend;
import fr.univmobile.it.commons.AppiumSafariEnabledTest;

public class AboutPageTest extends AppiumSafariEnabledTest {

	@Before
	public void setUpData() throws Exception {

		// "/tmp/unm-mobileweb/dataDir"
		final String dataDir = TestBackend.readMobilewebAppDataDir(new File(
				"target", "unm-mobileweb-app-local/WEB-INF/web.xml"));

		TestBackend.setUpData("001", new File(dataDir));
	}

	@Test
	public void testAboutPage_display() throws Exception {

		get("http://localhost:8380/unm-mobileweb/about/");

		waitForElementById(60, "div-info");

		takeScreenshot("about.png");

		savePageSource("about.html");
	}
}
