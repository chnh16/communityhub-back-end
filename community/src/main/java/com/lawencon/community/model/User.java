package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_user", 
uniqueConstraints = {
		@UniqueConstraint(name = "user_ck", columnNames = {"role_id", "profile_id"}
)})
public class User extends BaseEntity{
	
	@Column(length = 30, nullable = false)
	private String email;
	
	@Column(nullable = false, columnDefinition = "text")
	private String passwordUser;
	
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
}
