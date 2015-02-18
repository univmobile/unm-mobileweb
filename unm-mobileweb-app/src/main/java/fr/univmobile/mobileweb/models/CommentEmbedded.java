package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentEmbedded {
	
	@JsonProperty("_embedded")
	public CommentList _embedded;
}
