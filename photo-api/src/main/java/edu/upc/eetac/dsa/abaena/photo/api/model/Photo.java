package edu.upc.eetac.dsa.abaena.photo.api.model;

public class Photo {
	
	private int idphoto;
	private String user;
	private String autor;
	private String file;
	private String name;
	private String description; 
	private long timestamp;
	
	public int getIdphoto() {
		return idphoto;
	}
	public void setIdphoto(int idphoto) {
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
