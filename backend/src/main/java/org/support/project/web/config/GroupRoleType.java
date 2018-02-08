package org.support.project.web.config;


public enum GroupRoleType {
	NotAffiliation, //未所属
	Member,
	Manager;
	
	public int getValue() {
		return ordinal();
	}
	
	public static GroupRoleType getType(int type) {
		GroupRoleType[] values = values();
		return values[type];
	}
}
