package fr.univmobile.mobileweb.models;

import org.apache.commons.lang3.StringEscapeUtils;

public class Poi {
	
	private int id;
	private String name;
	private String description;
	private float lat;
	private float lng;
	private String markerType;
	private boolean active;
	private String logo;
	private String address;
	private String floor;
	private String zipcode;
	private String city;
	private String country;
	private String itinerary;
	private String openingHours;
	private String phones;
	private String email;
	private String url;
	//should be more here, add if needed
	private boolean iconRuedesfacs;
	private int universityId;
	private int categoryId;
	
	//additional fields
	private Category category;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public float getLat() {
		return lat;
	}
	
	public float getLng() {
		return lng;
	}
	
	public String getMarkerType() {
		return markerType;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getFloor() {
		return floor;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getItinerary() {
		return itinerary;
	}
	
	public String getOpeningHours() {
		return openingHours;
	}
	
	public String getPhones() {
		return phones;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getUrl() {
		return url;
	}
	
	//add other getters here
	
	public boolean isIconRuedesfacs() {
		return iconRuedesfacs;
	}
	
	public int getUniversityId() {
		return universityId;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public String escapeJS(String string) {
		return StringEscapeUtils.escapeEcmaScript(string);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
