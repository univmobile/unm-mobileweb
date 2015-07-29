package fr.univmobile.mobileweb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univmobile.mobileweb.models.Menu;
import fr.univmobile.mobileweb.models.MenuEmbedded;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.UsageStats;
import fr.univmobile.web.commons.AbstractJspController;

public abstract class AsbtractMobileWebJspController extends
		AbstractJspController {
	
	private RestTemplate restTemplate = null;
	
	private RestTemplate restTemplateJson = null;

	public RestTemplate restTemplate() {
		if (restTemplate == null) {
		    final ObjectMapper mapper = new ObjectMapper();
		    mapper.registerModule(new Jackson2HalModule());
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
	
		    final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		    converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
		    converter.setObjectMapper(mapper);
		    
		    restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
		}
		return restTemplate;
	}
	
	public RestTemplate restTemplateJson() {
		if (restTemplateJson == null) {
		    final ObjectMapper mapper = new ObjectMapper();
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
	
		    final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		    converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
		    converter.setObjectMapper(mapper);
		    
		    restTemplateJson = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
		}
		return restTemplateJson;
	}
	
	
	/*------------------------------------------------------------------
	 * Globally used methods
	 -------------------------------------------------------------------*/
	
	private HashMap<Integer, String> predefinedMenusMap;
	
	protected void setUniversity(String jsonUrl, int universityId) {
		University univObj = restTemplate().getForObject(jsonUrl + "/universities/" + universityId, University.class);

		setUniversity(jsonUrl, univObj);
	}
	
	private void setUniversity(String jsonUrl, University newUniversity) {
		University currentUniversity = getUniversity();
		if (currentUniversity != null && currentUniversity != newUniversity) {
			//when university is changed, menuContainer must be recreated
			if (hasSessionAttribute("menus")) {
				removeSessionAttribute("menus");
			}
		}
		setSessionAttribute("univ", newUniversity);

		// We track the selected university
		UsageStats usage = new UsageStats();
		usage.setSource("W");
		usage.setUniversity(jsonUrl + "/universities/" + newUniversity.getId());
		restTemplate().postForObject(jsonUrl + "/usageStats", usage, Object.class);


	}
	
	/**
	 * Gets currently selected university
	 */
	public University getUniversity() {
		if (hasSessionAttribute("univ")) {
			return getSessionAttribute("univ", University.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets logo URL of currently selected university
	 * @return university logo URL or empty string, if URL is null
	 */
	public String getUniversityLogo() {

		return getUniversity().getLogoUrl()!=null ? getUniversity().getLogoUrl() : "";
	}
	
	public Menu[] getMenuItems(String jsonUrl, String grouping) {
		
		//recreate menuContainer, if needed
		University tempUniversity = getUniversity();
		Menu[] menus = null;
		
		if (hasSessionAttribute("menus")) {
			getSessionAttribute("menus", Menu[].class);
		}
		
		if (menus == null) {
			
			//menus with university id
			MenuEmbedded menuContainerWithUniversity = restTemplate().getForObject(jsonUrl + "/menues/search/findAllForUniversity?universityId="+tempUniversity.getId()+"&size=100", MenuEmbedded.class);			
			if (menuContainerWithUniversity._embedded != null) {
				menus = menuContainerWithUniversity._embedded.getMenu();
			} else {
				menus = new Menu[0];
			}
			
			//join two arrays
			setSessionAttribute("menus", menus);
		}

		//if at least one menu item exists
		return filterMenuByGrouping(menus, grouping);
	}
	
	private Menu[] filterMenuByGrouping(Menu[] menu, String grouping) {
		
		ArrayList<Menu> filteredMenu = new ArrayList<Menu>();
		for (Menu menuItem : menu) {
			if (menuItem.getGrouping().equals(grouping)) {
				changePredefinedMenuUrl(menuItem);
				// Paris API and the "Bons plans are only displayed for"
				//if ( (menuItem.getId() == 21 || menuItem.getId() == 22) && getUniversity().getRegionId() == 1 ||  menuItem.getId() != 21 && menuItem.getId() != 22) {
					filteredMenu.add(menuItem);
				//}
			}
		}
		//sorting
		//filteredMenu = sortMenuByOrdinal(filteredMenu);
		return (Menu[]) filteredMenu.toArray(new Menu[filteredMenu.size()]);
	}
	
/*	private ArrayList<Menu> sortMenuByOrdinal(ArrayList<Menu> menu) {
		
		Collections.sort(menu, new Comparator<Menu>() {

			@Override
			public int compare(Menu m1, Menu m2) {
				//switch here for ascending/descending
				return m1.getOrdinal() - m2.getOrdinal();
			}
			
		});
		return menu;
	}*/
	
	private void changePredefinedMenuUrl(Menu menuItem) {
		if (getPredefinedMenusMap().containsKey(menuItem.getId())) {
			menuItem.setUrl(getPredefinedMenusMap().get(menuItem.getId()));
			menuItem.setPredefinedMenu(true);
		}
	}
	
	private HashMap<Integer, String> getPredefinedMenusMap() {
		
		if (predefinedMenusMap == null) {
			predefinedMenusMap = new HashMap<Integer, String>();
			//HashMap<id of menu, URL for menu>
			predefinedMenusMap.put(15, "profile");
			predefinedMenusMap.put(16, "libraries");
			predefinedMenusMap.put(17, "media");
			predefinedMenusMap.put(20, "university-map");
			predefinedMenusMap.put(21, "paris-map");
			predefinedMenusMap.put(22, "goodplans-map");
			predefinedMenusMap.put(49, "news");
		}
		return predefinedMenusMap;
	}

}
