package com.lawencon.community.constant;

public enum RoleList {
SUPERADMIN("Super Admin", "SPR"), ADMIN("ADMIN", "ADM"), SYSTEM("System", "SYS") , MEMBER("Member", "MBR");
	
	private final String roleName;
	private final String roleCode;
	
	private RoleList(final String roleName, final String roleCode) {
		this.roleName = roleName;
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}
	
	

	

}
