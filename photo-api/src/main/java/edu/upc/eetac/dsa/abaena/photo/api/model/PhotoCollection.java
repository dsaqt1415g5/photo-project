package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.ArrayList;
import java.util.List;

public class PhotoCollection {

	private List<Photo> photos = new ArrayList<>();

	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
}
