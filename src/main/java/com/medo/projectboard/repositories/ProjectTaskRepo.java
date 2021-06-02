package com.medo.projectboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medo.projectboard.domain.ProjectTask;

@Repository
public interface ProjectTaskRepo extends CrudRepository<ProjectTask, Long> {
	
//	ProjectTask getById(Long id);
}
