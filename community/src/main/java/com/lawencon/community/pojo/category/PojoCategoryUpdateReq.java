package com.lawencon.community.pojo.category;

public class PojoCategoryUpdateReq {
	private Long id;
	private String categoryCode;
	private String categoryName;
	private Integer ver;
	public Long getId() {
		return id;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public Integer getVer() {
		return ver;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	
	
}
