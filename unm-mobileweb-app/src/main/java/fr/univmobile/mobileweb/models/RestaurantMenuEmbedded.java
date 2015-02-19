package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestaurantMenuEmbedded {
	
	@JsonProperty("_embedded")
	public RestaurantMenuList _embedded;
}
