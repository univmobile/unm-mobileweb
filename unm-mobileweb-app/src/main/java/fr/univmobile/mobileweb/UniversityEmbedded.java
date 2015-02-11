package fr.univmobile.mobileweb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UniversityEmbedded {
	
	@JsonProperty("_embedded")
	public UniversityList _embedded;
}
