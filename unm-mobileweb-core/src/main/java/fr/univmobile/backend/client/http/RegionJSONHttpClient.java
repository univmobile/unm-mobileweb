package fr.univmobile.backend.client.http;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.avcompris.lang.NotImplementedException;

import fr.univmobile.backend.client.json.RegionJSONClient;

public class RegionJSONHttpClient implements RegionJSONClient {

	@Inject
	public RegionJSONHttpClient(final String url) {

		this.url = checkNotNull(url, "url");
	}

	private final String url;

	@Override
	public String getRegionsJSON() throws IOException {

		final HttpGet httpGet = new HttpGet(url);

		final CloseableHttpClient httpClient = HttpClients.createDefault();
		try {

			final CloseableHttpResponse response = httpClient.execute(httpGet);
			try {

				final HttpEntity entity = response.getEntity();

				return IOUtils.toString(entity.getContent(), UTF_8);

			} finally {
				response.close();
			}

		} finally {
			httpClient.close();
		}
	}

	@Override
	public String getUniversitiesJSONByRegion(final String regionId)
			throws IOException {

		throw new NotImplementedException();
	}
}
