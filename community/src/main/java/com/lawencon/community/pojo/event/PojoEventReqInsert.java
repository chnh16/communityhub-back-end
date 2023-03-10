package com.lawencon.community.pojo.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lawencon.community.pojo.file.PojoFileInsertReq;

public class PojoEventReqInsert {

	private String eventName;
	private String provider;
	private String locationName;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private BigDecimal price;
	private String categoryId;
	private PojoFileInsertReq file;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
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

	public void setFileId(PojoFileInsertReq file) {
		this.file = file;
	}

}
