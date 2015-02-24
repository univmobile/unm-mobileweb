package fr.univmobile.mobileweb.models;

import java.util.Date;

public class User {
	
	private int id;
	private String username;
	private String displayName;
	private String title;
	private String email;
	private String profileImageUrl;
	private String description;
	private Date notificationsReadDate;
	private boolean superAdmin;
	private boolean admin;
	private boolean student;
	private boolean librarian;
	
	public int getId() {
		return id;
	}
	
	public Date getNotificationsReadDate() {
		return notificationsReadDate;
	}
}
