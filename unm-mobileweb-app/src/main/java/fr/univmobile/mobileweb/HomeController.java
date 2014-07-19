package fr.univmobile.mobileweb;

import java.io.IOException;

@Paths({ "" })
public class HomeController extends AbstractController {

	@Override
	public String action() throws IOException {

		return "home.jsp";
	}
}
