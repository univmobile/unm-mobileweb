package fr.univmobile.mobileweb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuEmbedded {

	@JsonProperty("_embedded")
	public MenuList _embedded;
}
