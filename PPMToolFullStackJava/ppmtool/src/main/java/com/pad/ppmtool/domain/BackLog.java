package com.pad.ppmtool.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BackLog {
	@Id
	@GeneratedValue
	private Long id;
	private Integer PTSequence = 0;
	private String projectIdentifier;
	
	//OneToOne with project
	@OneToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="project_id", nullable = false)
	//Infinite recursion
	@JsonIgnore
	private Project project;
	

	//OneToMany with projectTasks(a backLog can have one or more ProjectTask but a ProjectTask can belong to one Project)
	@OneToMany(fetch=FetchType.EAGER, cascade= CascadeType.REFRESH, mappedBy="backLog")
	private List<ProjectTask> projectTasks = new ArrayList<>();


	public BackLog() {
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPTSequence() {
		return PTSequence;
	}
	public void setPTSequence(Integer pTSequence) {
		PTSequence = pTSequence;
	}
	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}
	
	
	
}
