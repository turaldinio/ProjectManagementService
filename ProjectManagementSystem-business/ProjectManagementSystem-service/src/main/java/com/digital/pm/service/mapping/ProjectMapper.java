package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.ProjectStatus;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.model.Project;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {
    public Project create(CreateProjectDto createProjectDto) {
        return Project.
                builder().
                description(createProjectDto.getDescription()).
                name(createProjectDto.getName()).
                status(ProjectStatus.DRAFT).
                projectCode(createProjectDto.getProjectCode()).
                build();
    }

    public Project update(Project project, CreateProjectDto createProjectDto) {
        if (Objects.nonNull(createProjectDto.getName())) {
            project.setName(createProjectDto.getName());
        }
        if (Objects.nonNull(createProjectDto.getProjectCode())) {
            project.setProjectCode(createProjectDto.getProjectCode());
        }
        if (Objects.nonNull(createProjectDto.getDescription())) {
            project.setDescription(createProjectDto.getDescription());
        }
        return project;
    }


    public ProjectDto map(Project project) {
        return ProjectDto.
                builder().
                id(project.getId()).
                description(project.getDescription()).
                name(project.getName()).
                status(project.getStatus()).
                projectCode(project.getProjectCode()).
                build();
    }

    public List<ProjectDto> map(List<Project> list) {
        return list.stream().map(project ->
                        ProjectDto.
                                builder().
                                id(project.getId()).
                                description(project.getDescription()).
                                name(project.getName()).
                                status(project.getStatus()).
                                projectCode(project.getProjectCode()).
                                build()).
                collect(Collectors.toList());
    }
}
