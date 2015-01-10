package edu.upc.eetac.dsa.abaena.photo.api.model;

public class User {
	
	
	private String username;
	private String password;
	private int avatar;
	private boolean RegisterSuccessful;
	
	

	public boolean isRegisterSuccessful() {
		return RegisterSuccessful;
	}
	public void setRegisterSuccessful(boolean registerSuccessful) {
		RegisterSuccessful = registerSuccessful;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAvatar() {
		return avatar;
	}
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}


	
}