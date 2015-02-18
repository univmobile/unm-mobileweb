package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewsEmbedded {
	
	@JsonProperty("_embedded")
	public NewsList _embedded;
}
