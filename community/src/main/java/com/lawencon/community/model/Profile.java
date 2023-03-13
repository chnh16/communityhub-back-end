package com.lawencon.community.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "profile", uniqueConstraints = {
		@UniqueConstraint(name = "profile_ck", columnNames = {"file_id", "industry_id", "position_id"}
)})

public class Profile extends BaseEntity {
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

	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;
	
	@OneToOne
	@JoinColumn(name = "industry_id", nullable = false)
	private Industry industry;
	
	@OneToOne
	@JoinColumn(name = "position_id", nullable = false)
	private Position position;
	private String company;

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

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	

}
