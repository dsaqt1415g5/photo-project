package edu.upc.eetac.dsa.abaena.photo.api.model;

public class Photo {
	
	private String idphoto;
	private String user;
	private String autor;
	private String file;
	private String name;
	private String description; 
	private long timestamp;
	private String photoURL;
	
	
	
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public String getIdphoto() {
		return idphoto;
	}
	public void setIdphoto(String idphoto) {
		this.idphoto = idphoto;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	
	
	
}
