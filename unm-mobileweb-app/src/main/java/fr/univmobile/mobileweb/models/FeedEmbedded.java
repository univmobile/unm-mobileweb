package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedEmbedded {

	@JsonProperty("_embedded")
	public FeedList _embedded;
}
