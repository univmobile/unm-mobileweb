package fr.univmobile.mobileweb.models;

import java.util.Date;

public class Notification {

	private int id;
	private String content;
	private Date notificationTime;
	private int universityId;
	
	public int getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getNotificationTime() {
		return notificationTime;
	}
	
	public int getUniversityId() {
		return universityId;
	}
}
