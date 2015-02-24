package fr.univmobile.mobileweb.models;

public class Bookmark {

	private int id;
	
	//additional field
	private Poi poi;
	private Category poiRootCategory;
	private String mapUrl;
	
	public int getId() {
		return id;
	}
	
	public Poi getPoi() {
		return poi;
	}
	
	public void setPoi(Poi poi) {
		this.poi = poi;
	}
	
	public Category getPoiRootCategory() {
		return poiRootCategory;
	}
	
	public void setPoiRootCategory(Category poiRootCategory) {
		this.poiRootCategory = poiRootCategory;
	}
	
	public String getMapUrl() {
		return mapUrl;
	}
	
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
}
