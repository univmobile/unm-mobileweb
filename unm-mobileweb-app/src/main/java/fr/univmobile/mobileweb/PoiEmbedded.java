package fr.univmobile.mobileweb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PoiEmbedded {
	
	@JsonProperty("_embedded")
	public PoiList _embedded;
}
