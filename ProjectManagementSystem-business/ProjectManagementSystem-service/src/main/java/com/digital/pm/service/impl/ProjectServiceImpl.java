package com.digital.pm.service.impl;

import com.digital.pm.common.enums.ProjectStatus;
import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.repository.spec.ProjectSpecification;
import com.digital.pm.repository.ProjectRepository;
import com.digital.pm.service.ProjectService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto create(CreateProjectDto createProjectDto) {
        if (projectRepository.existsByProjectCode(createProjectDto.getProjectCode())) {
            throw invalidProjectCode(createProjectDto.getProjectCode());
        }
        var project = projectMapper.create(createProjectDto);

        projectRepository.save(project);
        return projectMapper.map(project);
    }

    @Override
    public ProjectDto update(Long projectId, CreateProjectDto createProjectDto) {
        if (projectRepository.existsById(projectId)) {
            throw invalidId(projectId);
        }
        if (projectRepository.existsByProjectCode(createProjectDto.getProjectCode())) {
            throw invalidProjectCode(createProjectDto.getProjectCode());
        }

        var newProject = projectMapper.create(createProjectDto);
        newProject.setId(projectId);

        projectRepository.save(newProject);

        return projectMapper.map(newProject);
    }

    @Override
    public ProjectDto changeProjectStatus(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow(() -> invalidId(projectId));

        if (project.getStatus().equals(ProjectStatus.COMPLETED)) {
            throw new BadRequest("you cannot change the status for this project");
        }
        if (project.getStatus().equals(ProjectStatus.DRAFT)) {
            project.setStatus(ProjectStatus.DEVELOPING);
        }
        if (project.getStatus().equals(ProjectStatus.DEVELOPING)) {
            project.setStatus(ProjectStatus.TESTING);
        }
        if (project.getStatus().equals(ProjectStatus.TESTING)) {
            project.setStatus(ProjectStatus.COMPLETED);

        }
        return projectMapper.map(project);
    }

    @Override
    public List<ProjectDto> findAll(ProjectFilter projectFilter) {
        var result = projectRepository.
                findAll(ProjectSpecification.getSpec(projectFilter));

        return projectMapper.map(result);
    }

    public BadRequest invalidId(Long id) {
        return new BadRequest(String.format("the project with %d id is not found", id));
    }

    public BadRequest invalidProjectCode(String code) {
        return new BadRequest(String.format("the %s code is already exists", code));

    }
}

