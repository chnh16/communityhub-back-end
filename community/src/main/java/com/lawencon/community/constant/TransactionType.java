package com.lawencon.community.constant;

public enum TransactionType {
	EVENT("event"), COURSE("course"), MEMBERSHIP("membership");
	
	private final String typeName;
	
	private TransactionType(final String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}
}
