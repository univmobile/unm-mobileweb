package fr.univmobile.mobileweb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryEmbedded {
		
		@JsonProperty("_embedded")
		public CategoryList _embedded;
}
