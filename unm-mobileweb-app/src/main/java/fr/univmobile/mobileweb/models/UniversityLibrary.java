package fr.univmobile.mobileweb.models;

public class UniversityLibrary {

	private int id;
	private int universityId;
	private int poiId;
	
	//additional field
	private Poi poi;
	
	public int getId() {
		return id;
	}
	
	public int getUniversityId() {
		return universityId;
	}
	
	public int getPoiId() {
		return poiId;
	}
	
	public void setPoi(Poi poi) {
		this.poi = poi;
	}
	
	public Poi getPoi() {
		return poi;
	}
}
