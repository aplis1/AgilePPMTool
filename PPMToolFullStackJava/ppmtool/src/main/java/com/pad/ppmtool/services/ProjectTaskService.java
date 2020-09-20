package com.pad.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pad.ppmtool.domain.BackLog;
import com.pad.ppmtool.domain.Project;
import com.pad.ppmtool.domain.ProjectTask;
import com.pad.ppmtool.exceptions.ProjectNotFoundException;
import com.pad.ppmtool.repositories.BackLogRepository;
import com.pad.ppmtool.repositories.ProjectRepository;
import com.pad.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private BackLogRepository backLogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		try {
			
		//Exceptions: Project not found
		//ProjectTasks to be added to a specific project, project !=null and backlog exists
		BackLog backLog = backLogRepository.findByProjectIdentifier(projectIdentifier);
		 // set the backlog to projecttask
		projectTask.setBackLog(backLog);
		 // we want our project sequence to be like this - IDPRO-1, IDPRO-2,....IDPRO-100
		Integer backLogSequence = backLog.getPTSequence();
		 // update the Backlog sequence
		backLogSequence++;
		backLog.setPTSequence(backLogSequence);
		//Add Sequence to Project Task
		projectTask.setProjectSequence(projectIdentifier+"-"+backLogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		 // INITIAL Priority when priority is null
		if( projectTask.getPriority() == null) {
			projectTask.setPriority(3);
		}
		 // INITIAL Status when status is null
		if(projectTask.getStatus()== "" || projectTask.getStatus()== null) {
			projectTask.setStatus("TO-DO");
		}
		
		return projectTaskRepository.save(projectTask);
		}
		catch(Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}
		 
	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		
		Project project = projectRepository.findByProjectIdentifier(backlog_id);
		
		if(project == null) {
			throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' doens't exist");
		}
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
	
	public ProjectTask findProjectTaskByProjectSequence(String backlog_id, String projecttask_id) {
		//We want to make sure backlog exists
		BackLog backlog = backLogRepository.findByProjectIdentifier(backlog_id);
		if(backlog == null) {
			throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' doens't exist");
		}
		
		//make sure project task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projecttask_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task: '"+projecttask_id+"' not found");
		}
		
		//we want to make sure the correct projecttask id exists for the backlog
		if(!projectTask.getProjectIdentifier().equals(backlog_id.toUpperCase())) {
			throw new ProjectNotFoundException("Project Task: '"+projecttask_id+"' doesn't exist in project: '"+backlog_id);
		}
		return projectTask;
	}
	
	public ProjectTask updateProjectTaskBySequence(ProjectTask updateTask,String backlog_id, String projecttask_id ) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, projecttask_id);
		
		projectTask = updateTask;
		
		projectTaskRepository.save(projectTask);
		
		return updateTask;
	}
	
	public void deleteProjectTaskByProjectSequence(String backlog_id, String projecttask_id) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, projecttask_id);
		
		projectTaskRepository.delete(projectTask);
	}
	

}
