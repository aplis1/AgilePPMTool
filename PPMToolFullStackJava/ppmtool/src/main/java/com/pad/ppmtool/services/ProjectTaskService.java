package com.pad.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pad.ppmtool.domain.BackLog;
import com.pad.ppmtool.domain.ProjectTask;
import com.pad.ppmtool.repositories.BackLogRepository;
import com.pad.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private BackLogRepository backLogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
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
	
	

}
