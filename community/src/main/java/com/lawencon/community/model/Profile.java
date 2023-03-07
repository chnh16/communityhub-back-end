package com.lawencon.community.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "profile")
public class Profile extends BaseEntity{
	@Column(length = 30, nullable = false)
	private String fullName;
	
	@Column(length = 30, nullable = false)
	private String country;
	
	@Column(length = 30, nullable = false)
	private String province;
	
	@Column(length = 30, nullable = false)
	private String city;
	
	@Column(nullable = false)
	private Integer postalCode;
	
	@Column(length = 13, nullable = false)
	private String noHandphone;
	
	@Column(nullable = false)
	private BigDecimal balance;
	private LocalDateTime premiumUntil;
	
	@Column(name = "file_id", nullable = false)
	private File file;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}
	public String getNoHandphone() {
		return noHandphone;
	}
	public void setNoHandphone(String noHandphone) {
		this.noHandphone = noHandphone;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public LocalDateTime getPremiumUntil() {
		return premiumUntil;
	}
	public void setPremiumUntil(LocalDateTime premiumUntil) {
		this.premiumUntil = premiumUntil;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

}
