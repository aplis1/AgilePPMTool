package com.example.demo.security;

import java.util.Set;

import com.google.common.collect.Sets;


public enum ApplicationUserRole {
	
	STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(ApplicationUserPermissions.COURSE_READ, 
    		ApplicationUserPermissions.COURSE_WRITE, 
    		ApplicationUserPermissions.STUDENT_READ, 
    		ApplicationUserPermissions.STUDENT_WRITE)),
	ADMINTRAINEE(Sets.newHashSet(ApplicationUserPermissions.COURSE_READ));
	
	private Set<ApplicationUserPermissions> permissions;
	
	

	private ApplicationUserRole(Set<ApplicationUserPermissions> permissions) {
		this.permissions = permissions;
	}

	public Set<ApplicationUserPermissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<ApplicationUserPermissions> permissions) {
		this.permissions = permissions;
	}


	
	
	

}
