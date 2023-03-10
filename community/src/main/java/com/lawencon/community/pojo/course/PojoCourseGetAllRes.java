package com.lawencon.community.pojo.course;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lawencon.community.pojo.file.PojoFileInsertReq;

public class PojoCourseGetAllRes {
	private String id;
	private String courseName;
	private String provider;
	private String trainer;
	private String locationName;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private BigDecimal price;
	private String categoryId;
	private PojoFileInsertReq file;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getTrainer() {
		return trainer;
	}
	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public PojoFileInsertReq getFile() {
		return file;
	}
	public void setFile(PojoFileInsertReq file) {
		this.file = file;
	}
	
	
}
