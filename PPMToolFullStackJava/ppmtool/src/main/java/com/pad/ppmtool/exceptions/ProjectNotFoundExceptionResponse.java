package com.pad.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse {
	private String projectNotFound;

	public ProjectNotFoundExceptionResponse(String projectNotFound) {

		this.projectNotFound = projectNotFound;
	}

	public String getProjectNotFound() {
		return projectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		this.projectNotFound = projectNotFound;
	}
	
	

}
