package com.lawencon.community.pojo.industry;

public class PojoIndustryUpdateReq {
	private Long id;
	private String industryCode;
	private String industryName;
	private Integer ver;
	public Long getId() {
		return id;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public String getIndustryName() {
		return industryName;
	}
	public Integer getVer() {
		return ver;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}

	
}
