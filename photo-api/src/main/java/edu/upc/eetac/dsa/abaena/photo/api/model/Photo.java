package edu.upc.eetac.dsa.abaena.photo.api.model;

public class Photo {
	
	private int idphoto;
	private int iduser;
	private int idautor;
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
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public int getIdautor() {
		return idautor;
	}
	public void setIdautor(int idautor) {
		this.idautor = idautor;
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
