package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_user")
public class User extends BaseEntity{
	
	@Column(length = 30, nullable = false)
	private String email;
	
	@Column(nullable = false, columnDefinition = "text")
	private String passwordUser;
	
	@Column(nullable = false)
	private Boolean isVerified;
	
	@OneToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	@OneToOne
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;
	
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
}
