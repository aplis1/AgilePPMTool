package com.pad.ppmtool.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false, unique = true)
	private String projectSequence; //to search individual project tasks
	@NotBlank(message = "Please include a project summary")
	private String summary;//header of the project task
	private String acceptanceCriteria;
	private String status;
	private Integer priority;//group project tasks
	private Date dueDate;
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	private Date created_At;
	private Date updated_At;
	//ManyToOne with Backlog
	@ManyToOne(fetch= FetchType.EAGER, cascade= CascadeType.REFRESH)
	@JoinColumn(name="backlog_id", updatable = false, nullable= false )
	@JsonIgnore //infinite recursion
	private BackLog backLog;
	
	
	public ProjectTask() {
		
	}
	
	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
	}
	@PreUpdate
	protected void onUpdate() {
		this.updated_At = new Date();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectSequence() {
		return projectSequence;
	}
	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}
	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getCreated_At() {
		return created_At;
	}
	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}
	public Date getUpdated_At() {
		return updated_At;
	}
	public void setUpdated_At(Date updated_At) {
		this.updated_At = updated_At;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	

	public BackLog getBackLog() {
		return backLog;
	}

	public void setBackLog(BackLog backLog) {
		this.backLog = backLog;
	}

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", projectIdentifier=" + projectIdentifier + ", created_At=" + created_At
				+ ", updated_At=" + updated_At + "]";
	}
	
	

}
