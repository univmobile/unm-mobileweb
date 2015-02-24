package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserEmbedded {

	@JsonProperty("_embedded")
	public UserList _embedded;
}
