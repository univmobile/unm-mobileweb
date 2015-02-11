package fr.univmobile.mobileweb;

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
	//should be more here, add if needed
	private int universityId;
	
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
	
	//add other getters here
}
