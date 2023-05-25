package com.digital.pm.service;

import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.model.project.Project;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {
    ResponseEntity<?> create(CreateProjectDto createProjectDto);

    ResponseEntity<?>  update(Long projectId, CreateProjectDto createProjectDto);



    ResponseEntity<?>  changeProjectStatus(Long id);

    ResponseEntity<?>  findAll(ProjectFilter projectFilter);
}
