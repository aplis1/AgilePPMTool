package com.example.demo.security;

public enum ApplicationUserPermissions {
	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"),
	COURSE_READ("course:read"),
	COURSE_WRITE("course:write");

	private String permission;
		
		
	
	private ApplicationUserPermissions(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
		
	
}
