package com.lawencon.community.pojo.user;

import java.time.LocalDateTime;

public class PojoLoginRes {
	
	private String token;
	private String roleCode;
	private String idUser;
	private String fullName;
	private Boolean isVerified;
	private LocalDateTime premiumUntil;
	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public LocalDateTime getPremiumUntil() {
		return premiumUntil;
	}

	public void setPremiumUntil(LocalDateTime premiumUntil) {
		this.premiumUntil = premiumUntil;
	}

}
