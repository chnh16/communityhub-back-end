package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lawencon.base.BaseEntity;

@Entity
public class RegisterVerification extends BaseEntity {

	@Column(length = 30, nullable = false)
	private String email;

	@Column(length = 16, nullable = false)
	private String codeVerifcation;

	@Column(nullable = false)
	private Integer expired;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodeVerifcation() {
		return codeVerifcation;
	}

	public void setCodeVerifcation(String codeVerifcation) {
		this.codeVerifcation = codeVerifcation;
	}

	public Integer getExpired() {
		return expired;
	}

	public void setExpired(Integer expired) {
		this.expired = expired;
	}

}
