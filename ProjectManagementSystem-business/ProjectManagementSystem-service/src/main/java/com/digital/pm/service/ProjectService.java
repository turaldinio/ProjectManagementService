package com.digital.pm.service;

import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto create(CreateProjectDto createProjectDto);

    ProjectDto update(Long projectId, CreateProjectDto createProjectDto);

    ProjectDto getById(Long id);

    List<ProjectDto> getAll();

    void deleteById(Long id);

    ProjectDto findOne(ProjectFilter projectFilter);
}
