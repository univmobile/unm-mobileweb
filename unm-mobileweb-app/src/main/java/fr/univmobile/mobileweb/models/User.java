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
	public String getUsername() {
		return username;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getTitle() {
		return title;
	}
	public String getEmail() {
		return email;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public String getDescription() {
		return description;
	}
	public Date getNotificationsReadDate() {
		return notificationsReadDate;
	}
	public boolean isSuperAdmin() {
		return superAdmin;
	}
	public boolean isAdmin() {
		return admin;
	}
	public boolean isStudent() {
		return student;
	}
	public boolean isLibrarian() {
		return librarian;
	}
	
	
}
