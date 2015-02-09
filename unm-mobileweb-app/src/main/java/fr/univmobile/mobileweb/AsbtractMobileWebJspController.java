package fr.univmobile.mobileweb;

import java.util.Collections;

import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univmobile.web.commons.AbstractJspController;

public abstract class AsbtractMobileWebJspController extends
		AbstractJspController {

	public RestTemplate restTemplate() {
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new Jackson2HalModule());
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

	    final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
	    converter.setObjectMapper(mapper);
	    
	    return new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
	}

}
