package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginJSON {

	private String id;
	private String username;
	private String authenticationToken;
	
	public String getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	@JsonProperty("Authentication-Token")
	public String getAuthenticationToken() {
		return authenticationToken;
	}

}
