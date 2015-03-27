package fr.univmobile.mobileweb.models;

public class University {

	private int id;
	private String title;
	private String logoUrl;
	private String mobileShibbolethUrl;
	private int regionId;
	
	/** Central location for the university, to center correctly the map if there is no geolocalisation */
	private Double centralLat;
	private Double centralLng;
	
	private boolean crous;
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public String getLogoUrl() {
		return logoUrl;
	}

	public String getMobileShibbolethUrl() {
		return mobileShibbolethUrl;
	}

	public int getRegionId() {
		return regionId;
	}

	public Double getCentralLat() {
		return centralLat;
	}

	public Double getCentralLng() {
		return centralLng;
	}

	public boolean isCrous() {
		return crous;
	}
	
}
