package fr.univmobile.mobileweb.models;

import java.util.ArrayList;
import java.util.List;

public class ImageMap {

	private Long id;
	
	private String name;
	
	private String url;
	
	public int width;
	
	public int height;
	
	private List<Poi> pois;
	
	private Poi selectedPoi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addPoi(Poi poi) {
		if (pois == null) {
			pois = new ArrayList<Poi>();
		}
		pois.add(poi);
	}

	public Poi getSelectedPoi() {
		return selectedPoi;
	}

	public void setSelectedPoi(Poi selectedPoi) {
		this.selectedPoi = selectedPoi;
	}

	public List<Poi> getPois() {
		return pois;
	}

	public void setPois(List<Poi> pois) {
		this.pois = pois;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
