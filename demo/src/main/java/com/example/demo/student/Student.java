package com.example.demo.student;

public class Student {
	
	private Integer studentId;
	private String studentName;
	
	
	public Student(Integer studentId, String studentName) {
		this.studentId = studentId;
		this.studentName = studentName;
	}
	
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	

}
