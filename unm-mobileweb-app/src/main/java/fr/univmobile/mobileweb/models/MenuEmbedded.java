package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuEmbedded {

	@JsonProperty("_embedded")
	public MenuList _embedded;
}
