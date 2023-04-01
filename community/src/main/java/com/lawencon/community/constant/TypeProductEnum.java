package com.lawencon.community.constant;

public enum TypeProductEnum {
	MEMBERSHIP("MBRSH", "Membership"), 
	EVENT("EVENT", "Event"), 
	COURSE("COURS", "Course");
	
	private final String typeCode;
	private final String typeName;
	
	private TypeProductEnum(final String typeCode, final String typeName) {
		this.typeCode = typeCode;
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getTypeName() {
		return typeName;
	}
}
