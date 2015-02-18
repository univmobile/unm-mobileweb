package fr.univmobile.mobileweb.models;

public class Category {

	private int id;
	private String name;
	private String description;
	private boolean active;
	private String apiParisId;
	private String activeIconUrl;
	private String inactiveIconUrl;
	private String markerIconUrl;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String getApiParisId() {
		return apiParisId;
	}
	
	public String getActiveIconUrl() {
		return activeIconUrl;
	}
	
	public String getInactiveIconUrl() {
		return inactiveIconUrl;
	}
	
	public String getMarkerIconUrl() {
		return markerIconUrl;
	}
}
