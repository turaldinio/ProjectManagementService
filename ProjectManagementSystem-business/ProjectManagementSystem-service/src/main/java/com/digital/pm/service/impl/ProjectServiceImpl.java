package com.digital.pm.service.impl;

import com.digital.pm.common.enums.ProjectStatus;
import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.model.project.Project;
import com.digital.pm.repository.spec.ProjectSpecification;
import com.digital.pm.repository.ProjectRepository;
import com.digital.pm.service.ProjectService;
import com.digital.pm.service.mapping.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ResponseEntity<?> create(CreateProjectDto createProjectDto) {
        if (isProjectCodeUnique(createProjectDto.getProjectCode())) {
            return ResponseEntity.badRequest().body(String.format("the project with %s code is already exists", createProjectDto.getProjectCode()));
        }
        var project = projectMapper.create(createProjectDto);

        projectRepository.save(project);
        return ResponseEntity.ok(projectMapper.map(project));
    }

    @Override
    public ResponseEntity<?> update(Long projectId, CreateProjectDto createProjectDto) {
        if (projectRepository.existsById(projectId)) {
            return ResponseEntity.badRequest().body(String.format("the user with %d id is not found", projectId));
        }
        if (isProjectCodeUnique(createProjectDto.getProjectCode())) {
            return ResponseEntity.badRequest().body("the project with %s code is already exists");
        }
        var newProject = projectMapper.create(createProjectDto);
        newProject.setId(projectId);
        projectRepository.save(newProject);
        return ResponseEntity.ok(projectMapper.map(newProject));
    }

    @Override
    public ResponseEntity<?> changeProjectStatus(Long id) {
        var project = projectRepository.findById(id).orElseThrow();

        if (project.getStatus().equals(ProjectStatus.COMPLETED)) {
            return ResponseEntity.badRequest().body("you cannot change the status for this project");
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
        return ResponseEntity.ok(projectMapper.map(project));
    }

    @Override
    public ResponseEntity<?> findAll(ProjectFilter projectFilter) {
        var result = projectRepository.
                findAll(ProjectSpecification.getSpec(projectFilter));

        return ResponseEntity.ok(projectMapper.map(result));
    }

    public boolean isProjectCodeUnique(String code) {
        return projectRepository.existsByProjectCode(code);
    }
}

