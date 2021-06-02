package com.medo.projectboard.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medo.projectboard.domain.ProjectTask;
import com.medo.projectboard.services.ProjectTaskService;

@RestController
@RequestMapping("/api/v1/board")
@CrossOrigin
//@CrossOrigin(origins = "http://localhost:3000")
public class ProjectTaskController {
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@PostMapping("")
	public ResponseEntity<?> addPTToboard(@Valid @RequestBody ProjectTask projectTask, BindingResult result){
		
		if(result.hasErrors()) {
			
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error: result.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			
			return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.BAD_REQUEST);
			
		}
		
		
		ProjectTask newPT = projectTaskService.saveOrUpdateProjectTask(projectTask);
		
		return new ResponseEntity<ProjectTask>(newPT, HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public Iterable<ProjectTask> getAllPTs(){
		return projectTaskService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPTById(@PathVariable Long id){
		Optional<ProjectTask> projectTask = projectTaskService.findById(id);

		if(!projectTask.isPresent()) {
			Map<String, String> errorMap = new HashMap<>();
			errorMap.put("error", "there is no ProjectTask with id : " + id);
			
			return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Optional<ProjectTask>>(projectTask ,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable Long id) {
		Optional<ProjectTask> projectTask = projectTaskService.findById(id);
		if(!projectTask.isPresent()) {
			Map<String, String> errorMap = new HashMap<>();
			errorMap.put("error", "there is no ProjectTask with id : " + id);
			
			return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.NOT_FOUND);
		}
		
		projectTaskService.removeProjectTask(id);
		
		return new ResponseEntity<String>("ProjectTask has been deleted", HttpStatus.OK); 
	}
}
