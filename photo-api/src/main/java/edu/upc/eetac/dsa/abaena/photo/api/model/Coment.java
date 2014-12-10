package edu.upc.eetac.dsa.abaena.photo.api.model;

public class Coment {
	
	private int idcomment;
	private int iduser;
	private int idphoto;
	private long timestamp;
	private String content;
	
	
	public int getIdcomment() {
		return idcomment;
	}
	public void setIdcomment(int idcomment) {
		this.idcomment = idcomment;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public int getIdphoto() {
		return idphoto;
	}
	public void setIdphoto(int idphoto) {
		this.idphoto = idphoto;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}