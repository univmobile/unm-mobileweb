package fr.univmobile.mobileweb;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import fr.univmobile.backend.client.PoiClient;
import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.commons.DependencyInjection;
import fr.univmobile.web.commons.AbstractUnivMobileServlet;

public class UnivMobileServlet extends AbstractUnivMobileServlet {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = -3778148036214513471L;

	private RegionClient regions;
	
	private PoiClient pois;
	

	@Override
	public void init() throws ServletException {

		final Map<String, String> initParams = loadInitParams();

		final DependencyInjection dependencyInjection = new DependencyInjection(
				initParams);

		final String jsonURL = dependencyInjection.getInject(String.class).ref(
				"jsonURL");

		regions = dependencyInjection.getInject(RegionClient.class).into(
				UnivMobileServlet.class);

		pois = dependencyInjection.getInject(PoiClient.class).into(
				UnivMobileServlet.class);

		// --- Do not call the remote web service in init(), otherwise deadlock!
		// try {
		//
		// regions.getRegions();
		//
		// } catch (final IOException e) {
		// throw new ServletException(e);
		// }

		super.init( //
				new HomeController(), //
				new AboutController(jsonURL), //
				new RegionsController(regions), //
				new GeocampusController(regions,pois) //
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