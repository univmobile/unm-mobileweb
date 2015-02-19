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
	
	
	/*------------------------------------------------------------------
	 * Globally used methods
	 -------------------------------------------------------------------*/
	
	private University currentUniversity;
	private Menu[] menus;
	private HashMap<Integer, String> predefinedMenusMap;
	
	/**
	 * Gets currently selected university
	 */
	public University getUniversity() {
		
		University newUniversity = getSessionAttribute("univ", University.class);
		
		if (currentUniversity == null) {
			currentUniversity = newUniversity;
		} else {
			//when university is changed, menuContainer must be recreated
			if (currentUniversity != newUniversity) {
				menus = null;
				currentUniversity = newUniversity;
			}
		}
		return newUniversity;
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
		
		if (menus == null) {
			RestTemplate template = restTemplate();
			
			//menus with university id
			Menu[] menusWithUniversity = null;
			MenuEmbedded menuContainerWithUniversity = template.getForObject(jsonUrl + "/menues/search/findByUniversityOrderByCreatedOnDesc?universityId="+tempUniversity.getId(), MenuEmbedded.class);			
			if (menuContainerWithUniversity._embedded != null) {
				menusWithUniversity = menuContainerWithUniversity._embedded.getMenu();
			} else {
				menusWithUniversity = new Menu[0];
			}
			
			//menus without university id
			Menu[] menusWithoutUniversity = null;
			MenuEmbedded menuContainerWithoutUniversity = template.getForObject(jsonUrl + "/menues/search/findByUniversityOrderByCreatedOnDesc?universityId=null", MenuEmbedded.class);
			if (menuContainerWithoutUniversity._embedded != null) {
				menusWithoutUniversity = menuContainerWithoutUniversity._embedded.getMenu();
			} else {
				menusWithoutUniversity = new Menu[0];
			}
			
			//join two arrays
			menus = ArrayUtils.addAll(menusWithUniversity, menusWithoutUniversity);
		}

		//if at least one menu item exists
		if (menus.length > 0) {
			return filterMenuByGrouping(menus, grouping);
		} else {
			//must return empty array, because null cannot be set to attribute so it should be validated by attribute.length instead
			return new Menu[0];
		}
	}
	
	private Menu[] filterMenuByGrouping(Menu[] menu, String grouping) {
		
		ArrayList<Menu> filteredMenu = new ArrayList<Menu>();
		for (Menu menuItem : menu) {
			if (menuItem.getGrouping().equals(grouping)) {
				changePredefinedMenuUrl(menuItem);
				filteredMenu.add(menuItem);
			}
		}
		//sorting
		filteredMenu = sortMenuByOrdinal(filteredMenu);
		return (Menu[]) filteredMenu.toArray(new Menu[filteredMenu.size()]);
	}
	
	private ArrayList<Menu> sortMenuByOrdinal(ArrayList<Menu> menu) {
		
		Collections.sort(menu, new Comparator<Menu>() {

			@Override
			public int compare(Menu m1, Menu m2) {
				//switch here for ascending/descending
				return m1.getOrdinal() - m2.getOrdinal();
			}
			
		});
		return menu;
	}
	
	private void changePredefinedMenuUrl(Menu menuItem) {
		if (getPredefinedMenusMap().containsKey(menuItem.getId())) {
			menuItem.setUrl(getPredefinedMenusMap().get(menuItem.getId()));
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
		}
		return predefinedMenusMap;
	}

}
