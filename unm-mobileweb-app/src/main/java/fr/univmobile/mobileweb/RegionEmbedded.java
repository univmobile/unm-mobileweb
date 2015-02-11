package fr.univmobile.mobileweb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegionEmbedded {

	@JsonProperty("_embedded")
	public RegionList _embedded;
}
