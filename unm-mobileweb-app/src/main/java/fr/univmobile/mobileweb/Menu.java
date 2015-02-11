package fr.univmobile.mobileweb;

public class Menu {
	
	private int id;
	private String name;
	private boolean active;
	private int ordinal;
	private String url;
	private String content;
	private String grouping;
	private int universityId;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public int getOrdinal() {
		return ordinal;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getGrouping() {
		return grouping;
	}
	
	public int getUniversityId () {
		return universityId;
	}
	
}
