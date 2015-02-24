package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationEmbedded {
	@JsonProperty("_embedded")
	public NotificationList _embedded;
}
