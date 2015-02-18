package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UniversityEmbedded {
	
	@JsonProperty("_embedded")
	public UniversityList _embedded;
}
