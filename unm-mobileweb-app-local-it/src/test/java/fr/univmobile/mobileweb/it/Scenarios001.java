package fr.univmobile.mobileweb.it;

import static fr.univmobile.backend.core.impl.ConnectionType.MYSQL;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Before;
import org.junit.Test;

import fr.univmobile.backend.it.TestBackend;
import fr.univmobile.it.commons.AppiumSafariEnabledTest;
import fr.univmobile.it.commons.DeviceNames;
import fr.univmobile.it.commons.Scenario;
import fr.univmobile.it.commons.Scenarios;
import fr.univmobile.testutil.PropertiesUtils;

@Scenarios("Scénarios simples")
@DeviceNames({ "iPhone Retina (3.5-inch)", "iPhone Retina (4-inch)" })
public class Scenarios001 extends AppiumSafariEnabledTest {

	@Before
	public void setUpData() throws Exception {

		// "/tmp/unm-mobileweb/dataDir"
		final String dataDir = TestBackend.readMobilewebAppLocalDataDir(new File(
				"target", "unm-mobileweb-app-local/WEB-INF/web.xml"));

		final Connection cxn = DriverManager.getConnection(
				PropertiesUtils.getTestProperty("mysqlUrl"),
				PropertiesUtils.getTestProperty("mysqlUsername"),
				PropertiesUtils.getTestProperty("mysqlPassword"));
		try {

			TestBackend.setUpData("001", new File(dataDir), MYSQL, cxn);

		} finally {
			cxn.close();
		}
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
}
