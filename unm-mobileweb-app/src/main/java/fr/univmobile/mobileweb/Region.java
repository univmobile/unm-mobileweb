package fr.univmobile.mobileweb;

public class Region {
	
	private String id;

	private String name;
	
	private String label;
	
	private String url;
	
	private University[] universities;

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getUrl() {
		return url;
	}
	
	public University[] getUniversities() {
		return universities;
	}
	
	public void setUniversities(University[] universities) {
		this.universities = universities;
	}
	
}
