package fr.univmobile.mobileweb;

import javax.servlet.ServletException;

import fr.univmobile.web.commons.AbstractUnivMobileServlet;

public class UnivMobileServlet extends AbstractUnivMobileServlet {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = -3778148036214513471L;

	@Override
	public void init() throws ServletException {

		super.init( //
				new HomeController(), //
				new AboutController() //
		);
	}
}