package com.pad.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pad.ppmtool.domain.BackLog;
import com.pad.ppmtool.domain.Project;
import com.pad.ppmtool.exceptions.ProjectIdException;
import com.pad.ppmtool.repositories.BackLogRepository;
import com.pad.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BackLogRepository backLogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		String projectIdentifier = project.getProjectIdentifier().toUpperCase();
		try {
			project.setProjectIdentifier(projectIdentifier);
			if(project.getId() == null) {
				BackLog backLog = new BackLog();
				project.setBackLog(backLog);
				backLog.setProject(project);
				backLog.setProjectIdentifier(projectIdentifier);
			}
			
			if(project.getId() != null) {
				project.setBackLog(backLogRepository.findByProjectIdentifier(projectIdentifier));
			}
			
			return projectRepository.save(project);
			
		}catch(Exception e) {
			
			throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
		}	
		
	}
	
	public Project findProjectByIdentifier(String projectId) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Project ID '"+projectId+"' does not exists");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId);
		
		if(project == null) {
			throw new ProjectIdException("Project ID '"+projectId+"' does not exists to delete");
		}
		
		projectRepository.delete(project);
	}

}
