package com.digital.pm.service;

import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.model.project.Project;

import java.util.List;

public interface ProjectService {
    ProjectDto create(CreateProjectDto createProjectDto);

    ProjectDto update(Long projectId, CreateProjectDto createProjectDto);

    ProjectDto getById(Long id);

    List<ProjectDto> getAll();

     void changeProjectStatus(Project project);

    ProjectDto findOne(ProjectFilter projectFilter);
}
