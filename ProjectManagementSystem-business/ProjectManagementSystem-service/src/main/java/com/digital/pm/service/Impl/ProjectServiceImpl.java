package com.digital.pm.service.Impl;

import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
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

    @Override
    public ProjectDto create(CreateProjectDto createProjectDto) {
        var project = ProjectMapper.create(createProjectDto);

        projectRepository.save(project);
        return ProjectMapper.map(project);
    }

    @Override
    public ProjectDto update(Long projectId, CreateProjectDto createProjectDto) {
        var currentProject = projectRepository.findById(projectId).orElseThrow();

        currentProject = ProjectMapper.create(createProjectDto);
        projectRepository.save(currentProject);

        return ProjectMapper.map(currentProject);

    }

    @Override
    public ProjectDto getById(Long id) {
        var project = projectRepository.findById(id).orElseThrow();


        return ProjectMapper.map(project);
    }

    @Override
    public List<ProjectDto> getAll() {
        return ProjectMapper.map(projectRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);

    }

    @Override
    public ProjectDto findOne(ProjectFilter projectFilter) {

        var result = projectRepository.
                findOne(ProjectSpecification.getSpec(projectFilter)).
                orElseThrow();

        return ProjectMapper.map(result);
    }
}

