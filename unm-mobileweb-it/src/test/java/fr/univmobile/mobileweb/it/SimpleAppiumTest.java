package fr.univmobile.mobileweb.it;

import static fr.univmobile.backend.core.impl.ConnectionType.MYSQL;
import static fr.univmobile.it.commons.AppiumCapabilityType.DEVICE;
import static fr.univmobile.it.commons.AppiumCapabilityType.DEVICE_NAME;
import static fr.univmobile.it.commons.AppiumCapabilityType.PLATFORM_NAME;
import static fr.univmobile.it.commons.AppiumCapabilityType.PLATFORM_VERSION;
import static fr.univmobile.testutil.PropertiesUtils.getSettingsTestRefProperty;
import static fr.univmobile.testutil.PropertiesUtils.getTestProperty;
import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM;
import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fr.univmobile.backend.it.TestBackend;
import fr.univmobile.it.commons.EnvironmentUtils;

public class SimpleAppiumTest {

	@Before
	public void setUp() throws Exception {

		// "http://localhost:8380/unm-backend/"
		baseURL = TestBackend.readMobilewebAppBaseURL(new File("target",
				"unm-mobileweb-app/WEB-INF/web.xml"));

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

		final String logFile = TestBackend.readLog4jLogFile(new File("target",
				"unm-mobileweb-app/WEB-INF/classes/log4j.xml"));

		System.out.println("Log file: " + logFile);

		final String backendLogFile = TestBackend.readLog4jLogFile(new File(
				"target", "unm-backend-app/WEB-INF/classes/log4j.xml"));

		System.out.println("Backend Log file: " + backendLogFile);
	}

	private String baseURL;

	@Test
	public void testAppiumSimple() throws Exception {

		final DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability(BROWSER_NAME, "Safari");

		capabilities.setCapability(PLATFORM, "Mac");
		capabilities.setCapability(PLATFORM_NAME, "iOS");
		capabilities.setCapability(PLATFORM_VERSION,
				EnvironmentUtils.getCurrentPlatformVersion("iOS"));

		capabilities.setCapability(DEVICE, "iPhone Simulator");
		capabilities.setCapability(DEVICE_NAME, //
				"iPhone Retina (4-inch)");
		// "iPhone");
		// "iPhone Retina (3.5-inch)");

		// capabilities.setCapability(APP, "safari");

		final AppiumDriver driver = new AppiumDriver(new URL(
				"http://localhost:4723/wd/hub"), capabilities);
		try {

			driver.get(baseURL);

			new WebDriverWait(driver, 60).until(ExpectedConditions
					.presenceOfElementLocated(By.id( //
							"div-selectedUniversity")));

			final File file = // ((TakesScreenshot) augmentedDriver)
			driver.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(file, new File("target", "home.png"));

			file.delete();

			final String pageSource = driver.getPageSource();

			FileUtils.write(new File("target", "home.html"), //
					pageSource, UTF_8);

		} finally {
			driver.quit();
		}
	}
}
