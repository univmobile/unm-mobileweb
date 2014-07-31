package fr.univmobile.mobileweb;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import fr.univmobile.backend.client.RegionClient;
import fr.univmobile.commons.DependencyInjection;
import fr.univmobile.web.commons.AbstractUnivMobileServlet;

public class UnivMobileServlet extends AbstractUnivMobileServlet {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = -3778148036214513471L;

	private RegionClient regions;

	@Override
	public void init() throws ServletException {

		final Map<String, String> initParams = loadInitParams();

		regions = new DependencyInjection(initParams).getInject(
				RegionClient.class).into(UnivMobileServlet.class);

		try {

			regions.getRegions();

		} catch (final IOException e) {
			throw new ServletException(e);
		}

		super.init( //
				new HomeController(), //
				new AboutController(regions) //
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