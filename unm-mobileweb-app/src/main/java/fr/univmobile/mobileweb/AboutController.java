package fr.univmobile.mobileweb;

import static fr.univmobile.mobileweb.DataBeans.instantiate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Paths({ "about", "about/" })
public class AboutController extends AbstractController {

	@Override
	public String action() throws IOException {

		final Properties properties = new Properties();

		final InputStream is = getServletContext().getResourceAsStream(
				"META-INF/MANIFEST.MF");
		try {

			properties.load(is);

		} finally {
			is.close();
		}

		setAttribute("buildInfo", instantiate(BuildInfo.class) //
				.setBuildDisplayName(properties
				.getProperty("Buildinfo-BuildDisplayName")) //
				.setBuildId(properties
				.getProperty("Buildinfo-BuildId")) //
				.setGitCommitId(properties
				.getProperty("Buildinfo-Rev")));
		
		return "about.jsp";
	}
}

interface BuildInfo {

	String getBuildDisplayName();

	BuildInfo setBuildDisplayName(String buildDisplayName);

	String getBuildId();

	BuildInfo setBuildId(String buildId);

	String getGitCommitId();

	BuildInfo setGitCommitId(String gitCommitId);
}