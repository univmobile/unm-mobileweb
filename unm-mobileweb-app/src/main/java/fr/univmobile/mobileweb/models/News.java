package fr.univmobile.mobileweb.models;

import java.util.Date;

public class News {

	private int id;
	private String title;
	private String link;
	private String description;
	private String author;
	private String guid;
	private Date publishedDate;
	private String imageUrl;
	private String restoId;
	private String category;
	private String feedName;
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLink() {
		return link;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getGuid() {
		return guid;
	}
	
	public Date getPublishedDate() {
		return publishedDate;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getRestoId() {
		return restoId;
	}
	
	public String getCategory() {
		return category;
	}

	public String getFeedName() {
		return feedName;
	}
	
}
