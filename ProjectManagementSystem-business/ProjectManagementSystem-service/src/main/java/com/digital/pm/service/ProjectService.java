package com.digital.pm.service;

import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectService {
    @Transactional
    ProjectDto create(CreateProjectDto createProjectDto);

    @Transactional
    ProjectDto update(Long projectId, CreateProjectDto createProjectDto);

    ProjectDto changeProjectStatus(Long id);

    List<ProjectDto> findAll(ProjectFilter projectFilter);

    Boolean existsById(Long id);
}
