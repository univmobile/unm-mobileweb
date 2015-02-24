package fr.univmobile.mobileweb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UniversityLibraryEmbedded {

	@JsonProperty("_embedded")
	public UniversityLibraryList _embedded;
}
