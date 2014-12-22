package edu.upc.eetac.dsa.abaena.photo.api.model;

public class Coment {
	
	private int idcomment;
	private String username;
	private int idphoto;
	private long creationTimestamp;
	private String content;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public int getIdcomment() {
		return idcomment;
	}
	public void setIdcomment(int idcomment) {
		this.idcomment = idcomment;
	}

	public int getIdphoto() {
		return idphoto;
	}
	public void setIdphoto(int idphoto) {
		this.idphoto = idphoto;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}