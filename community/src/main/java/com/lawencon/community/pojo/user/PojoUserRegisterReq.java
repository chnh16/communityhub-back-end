package com.lawencon.community.pojo.user;

public class PojoUserRegisterReq {
	private String email;
	private String passwordUser;

	private PojoProfileInsertReq profile;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordUser() {
		return passwordUser;
	}

	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}

	public PojoProfileInsertReq getProfile() {
		return profile;
	}

	public void setProfile(PojoProfileInsertReq profile) {
		this.profile = profile;
	}

}
