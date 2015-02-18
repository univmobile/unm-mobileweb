package fr.univmobile.mobileweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import fr.univmobile.mobileweb.models.Comment;
import fr.univmobile.mobileweb.models.CommentEmbedded;

public class JsonServlet extends HttpServlet {

	private static final long serialVersionUID = 8454226267189579872L;
	
	private String jsonUrl;
	private final String DEFAULT_SIZE = "200";
	private final String DEFAULT_PAGE = "0";
	
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
		
		String actionParam = req.getParameter("action");
		String poiIdParam = req.getParameter("poiId");
		String sizeParam = req.getParameter("size");
		String pageParam = req.getParameter("page");
		
		if (sizeParam == null) {
			sizeParam = DEFAULT_SIZE;
		}
		if (pageParam == null) {
			pageParam = DEFAULT_PAGE;
		}
		
		//__________________________________________________________________________________________________________
		//for testing, remove later
		poiIdParam = "15";
		//__________________________________________________________________________________________________________
		
		
		if (actionParam != null && poiIdParam != null) {
			Object container = null;
			if (actionParam.equals("Comments")) {
				container = getComments(poiIdParam, sizeParam, pageParam);
			} else if (actionParam.equals("RestaurantMenu")) {
				
			}
			
			resp.setContentType("application/json;charset=UTF-8");
			
			// Use FasterXML Jackson library to generate JSON string from Javabeans
			ObjectMapper mapper=new ObjectMapper();
			
			PrintWriter out = resp.getWriter();
			
			mapper.writeValue(out, container);
			
			out.flush();
		}
	}
	
	private Object getComments(String poiId, String size, String page) {
		RestTemplate template = restTemplate();
		CommentEmbedded commentsContainer = template.getForObject(jsonUrl + "/comments/search/findByPoiOrderByCreatedOnDesc?poiId=" + poiId + "&size=" + size + "&page=" + page, CommentEmbedded.class);
		if (commentsContainer._embedded != null) {	
			return filterComments(commentsContainer._embedded.getComments());
		}
		return null;
	}
	
	private Comment[] filterComments(Comment[] comments) {
		
		ArrayList<Comment> filteredComments = new ArrayList<Comment>();
		for (Comment commentItem : comments) {
			if (commentItem.isActive()) {
				filteredComments.add(commentItem);
			}
		}
		return (Comment[]) filteredComments.toArray(new Comment[filteredComments.size()]);
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
