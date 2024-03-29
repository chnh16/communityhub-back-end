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
@Table(name = "t_event",
uniqueConstraints = {
		@UniqueConstraint(name = "event_ck", columnNames = {"category_id", "file_id", "user_id", "type_product_id"}
)})

public class Event extends BaseEntity {

	@Column(length = 100, nullable = false)
	private String eventName;

	@Column(length = 5, nullable = false)
	private String eventCode;

	@Column(length = 50, nullable = false)
	private String provider;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(length = 50, nullable = false)
	private String locationName;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	@Column(nullable = false)
	private BigDecimal price;

	@OneToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;
	
	@OneToOne
	@JoinColumn(name = "type_product_id", nullable = false)
	private TypeProduct typeProduct;

	public TypeProduct getTypeProduct() {
		return typeProduct;
	}

	public void setTypeProduct(TypeProduct typeProduct) {
		this.typeProduct = typeProduct;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
