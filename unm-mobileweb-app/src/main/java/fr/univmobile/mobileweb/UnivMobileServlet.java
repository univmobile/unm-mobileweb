package fr.univmobile.mobileweb;

import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnivMobileServlet extends HttpServlet {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = -3778148036214513471L;

	@Override
	public void init() throws ServletException {

		super.init();
	}
	
	@Override
	public void service(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException,
			ServletException {

		// 1. DISPATCH TO JSP

		final String jspPath = "/WEB-INF/jsp/home.jsp";

		final RequestDispatcher rd = request.getRequestDispatcher(jspPath);

		request.setCharacterEncoding(UTF_8);
		response.setContentType("text/html");
		response.setCharacterEncoding(UTF_8);
		// response.setHeader("Content-Language", "en");
		response.setLocale(Locale.FRENCH);

		rd.forward(request, response);
		
	}
}