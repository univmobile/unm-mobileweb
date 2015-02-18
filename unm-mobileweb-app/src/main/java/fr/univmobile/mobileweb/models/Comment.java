package fr.univmobile.mobileweb.models;

import java.util.Date;

public class Comment {

	private int id;
	private String title;
	private String message;
	private boolean active;
	private String author;
	private Date postedOn;
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public Date getPostedOn() {
		return postedOn;
	}
}
