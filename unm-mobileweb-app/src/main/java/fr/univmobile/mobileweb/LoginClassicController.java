package fr.univmobile.mobileweb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javax.annotation.Nullable;

import fr.univmobile.backend.client.AppToken;
import fr.univmobile.backend.client.ClientException;
import fr.univmobile.backend.client.SessionClient;
import fr.univmobile.web.commons.AbstractJspController;
import fr.univmobile.web.commons.HttpInputs;
import fr.univmobile.web.commons.HttpMethods;
import fr.univmobile.web.commons.HttpParameter;
import fr.univmobile.web.commons.HttpRequired;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "login/classic", "login/classic/" })
class LoginClassicController extends AbstractJspController {

	public LoginClassicController(final String apiKey,
			final SessionClient sessionClient) {

		this.apiKey = checkNotNull(apiKey, "apiKey");
		this.sessionClient = checkNotNull(sessionClient, "sessionClient");
	}

	private final String apiKey;
	private final SessionClient sessionClient;

	@Override
	@Nullable
	public View action() throws IOException, ClientException {

		final Login form = getHttpInputs(Login.class);

		if (form.isHttpValid()) {

			final AppToken appToken = sessionClient.login(apiKey, form.login(),
					form.password());

			if (appToken != null) {

				// Valid login

				setSessionAttribute("appToken", appToken);

				setAttribute("user", appToken.getUser());
				
				return new View("profile.jsp");
			}

			// Invalid login

			setAttribute("login",form.login());
			setAttribute("err_login", true);
		}

		return new View("login_classic.jsp");
	}

	@HttpMethods("POST")
	interface Login extends HttpInputs {

		@HttpRequired
		@HttpParameter
		String login();

		@HttpRequired
		@HttpParameter
		String password();
	}
}
