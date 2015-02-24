package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LinkEmbedded {

	@JsonProperty("_embedded")
	public LinkList _embedded;
}
