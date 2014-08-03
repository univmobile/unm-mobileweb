package fr.univmobile.mobileweb.it;

import org.junit.Test;

import fr.univmobile.it.commons.AppiumSafariEnabledTest;
import fr.univmobile.it.commons.DeviceNames;
import fr.univmobile.it.commons.Scenario;
import fr.univmobile.it.commons.Scenarios;

@Scenarios("Scénarios simples")
@DeviceNames({ "iPhone Retina (3.5-inch)", "iPhone Retina (4-inch)" })
public class Scenarios001 extends AppiumSafariEnabledTest {

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
}
