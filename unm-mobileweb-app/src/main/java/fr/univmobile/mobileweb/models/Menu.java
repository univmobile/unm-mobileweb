package fr.univmobile.mobileweb.models;

public class Menu {
	
	private int id;
	private String name;
	private boolean active;
	private int ordinal;
	private String url;
	private String content;
	private String grouping;
	private int universityId;
	
	//whether menu id is predefined as system menu, e.g. profile, geocampus, etc.
	private boolean predefinedMenu = false;
	
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
	
	public void setUrl(String url) {
		this.url = url;
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
	
	public boolean isPredefinedMenu() {
		return predefinedMenu;
	}
	
	public void setPredefinedMenu(boolean predefinedMenu) {
		this.predefinedMenu = predefinedMenu;
	}
	
}
