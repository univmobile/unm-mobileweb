package fr.univmobile.mobileweb.models;

import java.util.Date;

public class RestaurantMenu {

	private int id;
	private Date effectiveDate;
	private String description;
	
	public int getId() {
		return id;
	}
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	
	public String getDescription() {
		return description;
	}
}
