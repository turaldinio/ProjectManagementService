package com.digital.pm.service.impl;

import com.digital.pm.common.enums.ProjectStatus;
import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.model.project.Project;
import com.digital.pm.repository.spec.ProjectSpecification;
import com.digital.pm.repository.ProjectRepository;
import com.digital.pm.service.ProjectService;
import com.digital.pm.service.mapping.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto create(CreateProjectDto createProjectDto) {
        var project = projectMapper.create(createProjectDto);

        projectRepository.save(project);
        return projectMapper.map(project);
    }

    @Override
    public ProjectDto update(Long projectId, CreateProjectDto createProjectDto) {
        var currentProject = projectRepository.findById(projectId).orElseThrow();

        currentProject = projectMapper.create(createProjectDto);
        projectRepository.save(currentProject);

        return projectMapper.map(currentProject);

    }

    @Override
    public ProjectDto getById(Long id) {
        var project = projectRepository.findById(id).orElseThrow();


        return projectMapper.map(project);
    }

    @Override
    public List<ProjectDto> getAll() {
        return projectMapper.map(projectRepository.findAll());
    }

    @Override
    public void changeProjectStatus(Project project) {
        if (project.getStatus().equals(ProjectStatus.DRAFT)) {
            project.setStatus(ProjectStatus.DEVELOPING);
            return;
        }
        if (project.getStatus().equals(ProjectStatus.DEVELOPING)) {
            project.setStatus(ProjectStatus.TESTING);
            return;

        }
        if (project.getStatus().equals(ProjectStatus.TESTING)) {
            project.setStatus(ProjectStatus.COMPLETED);

        }
    }

    @Override
    public ProjectDto findOne(ProjectFilter projectFilter) {

        var result = projectRepository.
                findOne(ProjectSpecification.getSpec(projectFilter)).
                orElseThrow();

        return projectMapper.map(result);
    }
}

