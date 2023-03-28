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
@Table(name = "t_transaction", uniqueConstraints = {
		@UniqueConstraint(name = "t_transaction_ck", columnNames = {"file_id", "user_id", "event_id", "course_id", "membership_id", "voucher_id", "status_transaction_id"}
)})
public class Transaction extends BaseEntity {

	@Column(nullable = false)
	private LocalDateTime transactionDate;

	@Column(nullable = false)
	private BigDecimal grandTotal;

	@Column(nullable = false)
	private Boolean isApproved;
	
	@Column(length = 8 ,nullable = false)
	private String transactionCode;
	
	@OneToOne
	@JoinColumn(name = "status_transaction_id")
	private StatusTransaction statusTransaction;

	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@OneToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@OneToOne
	@JoinColumn(name = "membership_id")
	private Membership membership;

	@OneToOne
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
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

	public Event getEvent() {
		return event;
	}

	public StatusTransaction getStatusTransaction() {
		return statusTransaction;
	}

	public void setStatusTransaction(StatusTransaction statusTransaction) {
		this.statusTransaction = statusTransaction;
	}
	

	public void setEvent(Event event) {
		this.event = event;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

}
