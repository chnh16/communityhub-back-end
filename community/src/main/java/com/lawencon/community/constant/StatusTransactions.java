package com.lawencon.community.constant;

public enum StatusTransactions {
PENDING("Pending", "PDG"), APPROVE("Approve", "APR"), REJECTED("Rejected", "RJC");
	
	private final String statusName;
	private final String statusCode;
	
	private StatusTransactions(final String statusName, final String statusCode) {
		this.statusName = statusName;
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public String getStatusCode() {
		return statusCode;
	}

}
