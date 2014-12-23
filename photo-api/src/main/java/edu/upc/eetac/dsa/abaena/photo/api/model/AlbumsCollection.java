package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.ArrayList;
import java.util.List;

public class AlbumsCollection {

	private List<Albums> albums;

	public List<Albums> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Albums> albums) {
		this.albums = albums;
	}
	public AlbumsCollection() {
		super();
		albums = new ArrayList<>();
	}
	
	public void addAlbum(Albums album) {
		albums.add(album);
	}
	
}
