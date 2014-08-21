package fr.univmobile.mobileweb.it;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import fr.univmobile.backend.it.TestBackend;
import fr.univmobile.it.commons.AppiumSafariEnabledTest;
import fr.univmobile.it.commons.DeviceNames;
import fr.univmobile.it.commons.Scenario;
import fr.univmobile.it.commons.Scenarios;

@Scenarios("Scénarios simples")
@DeviceNames({ "iPhone Retina (3.5-inch)", "iPhone Retina (4-inch)" })
public class Scenarios001 extends AppiumSafariEnabledTest {

	@Before
	public void setUpData() throws Exception {

		// "/tmp/unm-mobileweb/dataDir"
		final String dataDir = TestBackend.readBackendAppDataDir(new File(
				"target", "unm-backend-app/WEB-INF/web.xml"));

		TestBackend.setUpData("001", new File(dataDir));
	}

	@Scenario("Aller-retour sur la page « À Propos »")
	@Test
	public void sc001() throws Exception {

		get("http://localhost:8380/unm-mobileweb/");

		waitForElementById(60, "div-selectedUniversity");

		takeScreenshot("home.png");

		elementById("h1-title").textShouldEqualTo("UnivMobile");

		elementById("link-about").click();

		waitForElementById(60, "div-info");

		takeScreenshot("about.png");

		elementById("link-ok").click();

		waitForElementById(60, "div-selectedUniversity");

		takeScreenshot("home2.png");
	}

	@Scenario("Aller-retour sur la page « Régions »")
	@Test
	public void Choix_de_region_000() throws Exception {

		get("http://localhost:8380/unm-mobileweb/");

		waitForElementById(60, "div-selectedUniversity");

		takeScreenshot("home.png");

		elementById("link-choisir").click();

		waitForElementById(60, "div-back");

		takeScreenshot("regions.png");

		elementById("link-back").click();

		waitForElementById(60, "div-selectedUniversity");

		takeScreenshot("home2.png");
	}

	@Scenario("Géocampus")
	@Test
	public void Geocampus_000() throws Exception {

		get("http://localhost:8380/unm-mobileweb/");

		waitForElementById(60, "div-selectedUniversity");

		takeScreenshot("home.png");

		elementById("link-geocampus").click();

		waitForElementById(60, "div-back");

		takeScreenshot("geocampus_list.png");

		elementById("link-tab_map").click();

		pause(10000);

		takeScreenshot("geocampus_map.png");

		elementById("link-poiNav-3792").click(); // Cergy Pointoise

		pause(2000);

		takeScreenshot("geocampus_map_infoWindow.png");
		
		elementById("div-infoWindow-3792").click();

		pause(2000);

		takeScreenshot("geocampus_details.png");
		
		elementById("link-up").click();

		pause(10000);

		takeScreenshot("geocampus_back_to_map.png");
	}
}
