package edu.upc.eetac.dsa.abaena.photo.api.model;

public class PhotoError {
	private int status;
	private String message;
 
	public PhotoError() {
		super();
	}
 
	public PhotoError(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
 
	public int getStatus() {
		return status;
	}
 
	public void setStatus(int status) {
		this.status = status;
	}
 
	public String getMessage() {
		return message;
	}
 
	public void setMessage(String message) {
		this.message = message;
	}
}
