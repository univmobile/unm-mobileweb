package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookmarkEmbedded {

	@JsonProperty("_embedded")
	public BookmarkList _embedded;
}
