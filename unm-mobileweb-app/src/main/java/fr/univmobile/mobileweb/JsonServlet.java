package fr.univmobile.mobileweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonServlet extends HttpServlet {

	private static final long serialVersionUID = 8454226267189579872L;
	
	private String jsonUrl;
	
	public JsonServlet() {
		super();
	}
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		// Set the json Url value
		this.jsonUrl = servletConfig.getInitParameter("inject:String ref:jsonURL");
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Get the list of region
		RestTemplate template = restTemplate();

		RegionEmbedded regionContainer = template.getForObject(
				"http://vps111534.ovh.net:8082/regions", RegionEmbedded.class);

		int i = 0;
		for (Region region : regionContainer._embedded.getRegions()) {
			i++;
			UniversityEmbedded universityContainer = template.getForObject(
					jsonUrl + "/regions/" + i/* region.getId() */
							+ "/universities", UniversityEmbedded.class);
			region.setUniversities(universityContainer._embedded
					.getUniversities());
		}
		
		resp.setContentType("application/json");
		
		// Use FasterXML Jackson library to generate JSON string from Javabeans
		ObjectMapper mapper=new ObjectMapper();
		
		PrintWriter out = resp.getWriter();
		
		mapper.writeValue(out, regionContainer);
		
		out.flush();
	}

	public RestTemplate restTemplate() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Jackson2HalModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
				false);

		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType
				.parseMediaTypes("application/hal+json"));
		converter.setObjectMapper(mapper);

		return new RestTemplate(
				Collections.<HttpMessageConverter<?>> singletonList(converter));
	}
}
