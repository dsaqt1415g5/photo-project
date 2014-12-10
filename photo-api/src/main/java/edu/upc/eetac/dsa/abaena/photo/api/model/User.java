package edu.upc.eetac.dsa.abaena.photo.api.model;

public class User {
	
	private int userid;
	private String username;
	private String password;
	private int avatar;
	private int idprofile;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
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
	public int getIdprofile() {
		return idprofile;
	}
	public void setIdprofile(int idprofile) {
		this.idprofile = idprofile;
	}

	
}