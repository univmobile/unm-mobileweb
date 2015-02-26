package fr.univmobile.mobileweb.models;

public class CommentJson {

	private String title;
	private String message;
	private String active;
	private String poi;
	
	public CommentJson(String title, String message, String active, String poi) {
		
		this.title = title;
		this.message = message;
		this.active = active;
		this.poi = poi;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPoi() {
		return poi;
	}

	public void setPoi(String poi) {
		this.poi = poi;
	}
	
	
}
