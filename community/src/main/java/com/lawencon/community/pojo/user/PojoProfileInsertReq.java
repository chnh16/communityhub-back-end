package com.lawencon.community.pojo.user;

import com.lawencon.community.pojo.file.PojoFileInsertReq;

public class PojoProfileInsertReq {
	private String fullName;
	private String country;
	private String province;
	private String city;
	private String phoneNumber;
	private String postalCode;
	private String positionId;
	private String industryId;
	private String company;
	private PojoFileInsertReq file;
	
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public PojoFileInsertReq getFile() {
		return file;
	}
	public void setFile(PojoFileInsertReq file) {
		this.file = file;
	}
}
