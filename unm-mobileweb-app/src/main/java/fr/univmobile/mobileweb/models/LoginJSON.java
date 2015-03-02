package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginJSON {

	private String uid;

	private String authenticationToken;
	
	@JsonProperty("user.uid")
	public String getId() {
		return uid;
	}
	
	@JsonProperty("id")
	public String getAuthenticationToken() {
		return authenticationToken;
	}

}
