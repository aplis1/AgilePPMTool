package com.pad.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pad.ppmtool.domain.ProjectTask;
import com.pad.ppmtool.services.MapValidationErrorService;
import com.pad.ppmtool.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BackLogController {
	@Autowired
	private ProjectTaskService projectTaskService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjecTaskToBackLog(@Valid @RequestBody ProjectTask projectTask,
			BindingResult bindingResult, @PathVariable String backlog_id){
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
		
		if(errorMap != null) return errorMap;
		
		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);
		
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
			
	}
	
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
		return projectTaskService.findBacklogById(backlog_id);
	}
	
	@GetMapping("/{backlog_id}/{projecttask_id}")
	public ResponseEntity<?> getProjectTaskBySequence(@PathVariable String backlog_id, @PathVariable String projecttask_id ){
		ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, projecttask_id);
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);	
	}
	
	@PatchMapping("/{backlog_id}/{projecttask_id}")
	public ResponseEntity<?> updateProjectBySequence(@Valid @RequestBody ProjectTask updateTask, 
			@PathVariable String backlog_id, @PathVariable String projecttask_id){
		ProjectTask projectTask = projectTaskService.updateProjectTaskBySequence(updateTask, backlog_id, projecttask_id);
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}

}
