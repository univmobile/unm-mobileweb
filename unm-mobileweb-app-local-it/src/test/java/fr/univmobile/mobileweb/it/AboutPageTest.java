package fr.univmobile.mobileweb.it;

import org.junit.Test;

import fr.univmobile.it.commons.AppiumSafariEnabledTest;

public class AboutPageTest extends AppiumSafariEnabledTest {

	@Test
	public void testAboutPage_display() throws Exception {

		get("http://localhost:8380/unm-mobileweb/about/");

		waitForElementById(60, "div-info");

		takeScreenshot("about.png");

		savePageSource("about.html");
	}
}
