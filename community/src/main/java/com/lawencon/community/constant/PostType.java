package com.lawencon.community.constant;

public enum PostType {
	
NORMAL("Normal", "NRM"), POLLING("Polling", "PLG"), PREMIUM("Premium", "PRM");
	
	private final String typeCode;
	private final String typeName;
	
	private PostType(final String typeName, final String typeCode) {
		this.typeName = typeName;
		this.typeCode = typeCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getTypeName() {
		return typeName;
	}
	
	

	
}
