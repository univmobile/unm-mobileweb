package fr.univmobile.mobileweb;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import fr.univmobile.backend.client.HomeClient;
import fr.univmobile.backend.client.PoiClient;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.backend.client.SessionClient;
import fr.univmobile.commons.DependencyInjection;
import fr.univmobile.web.commons.AbstractUnivMobileServlet;

public class UnivMobileServlet extends AbstractUnivMobileServlet {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = -3778148036214513471L;

	// private RegionClient regions;
	// private PoiClient pois;
	// private HomeClient home;

	@Override
	public void init() throws ServletException {

		final Map<String, String> initParams = loadInitParams();

		final DependencyInjection dependencyInjection = new DependencyInjection(
				initParams);

		final String jsonURL = dependencyInjection.getInject(String.class).ref(
				"jsonURL");
		
		final String universiteCategoryId = dependencyInjection.getInject(String.class).ref(
				"universiteCategoryId");
		
		final String bonplansCategoryId = dependencyInjection.getInject(String.class).ref(
				"bonplansCategoryId");
		
		final String parisCategoryId = dependencyInjection.getInject(String.class).ref(
				"parisCategoryId");
		
		final String restaurationUniversitaireCategoryId = dependencyInjection.getInject(String.class).ref(
				"restaurationUniversitaireCategoryId");
		
		final String librariesCategoryId = dependencyInjection.getInject(String.class).ref(
				"librariesCategoryId");
	
		final HomeClient home = dependencyInjection.getInject(HomeClient.class)
				.into(UnivMobileServlet.class);

		final RegionClient regions = dependencyInjection.getInject(
				RegionClient.class).into(UnivMobileServlet.class);

		final PoiClient pois = dependencyInjection.getInject(PoiClient.class)
				.into(UnivMobileServlet.class);

		final SessionClient sessions = dependencyInjection.getInject(
				SessionClient.class).into(UnivMobileServlet.class);

		// --- Do not call the remote web service in init(), otherwise deadlock!
		// try {
		//
		// regions.getRegions();
		//
		// } catch (final IOException e) {
		// throw new ServletException(e);
		// }

		final String apiKey = "toto";

		super.init( //
				new HomeController(jsonURL, apiKey, sessions,regions), //
				new LoginController(jsonURL), //
				new LoginClassicController(apiKey, sessions), //
				new LoginShibbolethController(apiKey, sessions, regions), //
				new AboutController(jsonURL, home), //
				new RegionsController(regions), //
				new UniversityMapController(jsonURL, universiteCategoryId, restaurationUniversitaireCategoryId, librariesCategoryId), //
				new GoodPlansMapController(jsonURL, bonplansCategoryId, restaurationUniversitaireCategoryId), //
				new ParisMapController(jsonURL, parisCategoryId, restaurationUniversitaireCategoryId), //
				new ProfileController(jsonURL, universiteCategoryId, bonplansCategoryId, parisCategoryId), //
				new NewsController(jsonURL), //
				new MenuContentController(jsonURL), //
				new MediaController(jsonURL), //
				new LibrariesController(jsonURL), //
				new BookmarksController(jsonURL, universiteCategoryId, bonplansCategoryId, parisCategoryId), //
				new NotificationsController(jsonURL) //
		);
	}

	private Map<String, String> loadInitParams() {

		final Map<String, String> params = new HashMap<String, String>();

		final ServletConfig servletConfig = getServletConfig();

		for (final Enumeration<?> e = servletConfig.getInitParameterNames(); e
				.hasMoreElements();) {

			final String name = (String) e.nextElement();

			final String value = servletConfig.getInitParameter(name);

			params.put(name, value);
		}

		return params;
	}
}