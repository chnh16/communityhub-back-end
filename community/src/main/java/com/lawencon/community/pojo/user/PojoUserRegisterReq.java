package com.lawencon.community.pojo.user;

public class PojoUserRegisterReq {
	private String email;
	private String password;
	private PojoProfileInsertReq profile;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public PojoProfileInsertReq getProfile() {
		return profile;
	}
	public void setProfile(PojoProfileInsertReq profile) {
		this.profile = profile;
	}
	
	
}
