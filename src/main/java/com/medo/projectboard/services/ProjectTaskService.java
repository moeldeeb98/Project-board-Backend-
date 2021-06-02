package com.medo.projectboard.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medo.projectboard.domain.ProjectTask;
import com.medo.projectboard.repositories.ProjectTaskRepo;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepo projectTaskRepo;
	
	public ProjectTask saveOrUpdateProjectTask(ProjectTask projectTask) {
		
		if(projectTask.getStatus() == null || projectTask.getStatus() == "") {
			projectTask.setStatus("TO_DO");
		}
		
		return projectTaskRepo.save(projectTask);
	}
	
	public Iterable<ProjectTask> findAll(){
		return projectTaskRepo.findAll();
	}
	
	public Optional<ProjectTask> findById(Long id) {
//		return projectTaskRepo.getById(id);
		return projectTaskRepo.findById(id);
	}
	
	public void removeProjectTask(Long id){
		projectTaskRepo.deleteById(id);
	}
}
