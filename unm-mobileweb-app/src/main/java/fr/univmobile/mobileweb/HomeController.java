package fr.univmobile.mobileweb;

import java.io.IOException;

import fr.univmobile.web.commons.AbstractController;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;

@Paths({ "" })
public class HomeController extends AbstractController {

	@Override
	public String action() throws IOException {

		return "home.jsp";
	}
	
	@HttpMethods("GET")
	private interface SelectedUniversity extends HttpInputs {

		@HttpRequired
		@HttpParameter(trim = true)
		String region();

		@HttpRequired
		@HttpParameter(trim = true)
		String univ();
	}
}
