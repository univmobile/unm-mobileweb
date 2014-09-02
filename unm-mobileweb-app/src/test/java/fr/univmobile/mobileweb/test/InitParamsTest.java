package fr.univmobile.mobileweb.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.avcompris.binding.annotation.Namespaces;
import net.avcompris.binding.annotation.XPath;
import net.avcompris.binding.dom.helper.DomBinderUtils;

import org.junit.Before;
import org.junit.Test;

import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.RegionClientFromJSON;
import fr.univmobile.backend.client.http.RegionJSONHttpClient;
import fr.univmobile.backend.client.json.RegionJSONClient;
import fr.univmobile.commons.DependencyInjection;
import fr.univmobile.mobileweb.UnivMobileServlet;

public class InitParamsTest {

	@Before
	public void setUp() throws Exception {

		final Map<String, String> initParams = DomBinderUtils.xmlContentToJava(
				new File("src/main/webapp/WEB-INF/web.xml"), InitParams.class)
				.getInitParams();

		di = new DependencyInjection(initParams);
	}

	private DependencyInjection di;

	@Test
	public void testInitParams() throws IOException {

		assertEquals(RegionClientFromJSON.class,
				di.getInject(RegionClient.class).into(UnivMobileServlet.class)
						.getClass());

		assertEquals(
				RegionJSONHttpClient.class,
				di.getInject(RegionJSONClient.class)
						.into(RegionClientFromJSON.class).getClass());
	}

	@Namespaces("xmlns:j2ee=http://java.sun.com/xml/ns/j2ee")
	@XPath("/j2ee:web-app/j2ee:servlet")
	public interface InitParams {

		@XPath(value = "j2ee:init-param", //
		mapKeysType = String.class, mapValuesType = String.class, //
		mapKeysXPath = "j2ee:param-name", mapValuesXPath = "j2ee:param-value")
		Map<String, String> getInitParams();
	}
}
