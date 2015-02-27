package fr.univmobile.mobileweb.models;

public class Bookmark {

	private int id;
	private int poiId;
	private int poiUniversityId;
	private String poiName;
	private int poiCategoryId;
	private int rootCategoryId;
	
	//additional field
	private String mapUrl;
	
	public int getId() {
		return id;
	}

	public int getPoiId() {
		return poiId;
	}

	public int getPoiUniversityId() {
		return poiUniversityId;
	}

	public String getPoiName() {
		return poiName;
	}

	public int getPoiCategoryId() {
		return poiCategoryId;
	}

	public int getRootCategoryId() {
		return rootCategoryId;
	}

	public String getMapUrl() {
		return mapUrl;
	}
	
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
}
