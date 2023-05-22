package com.digital.pm.service.mapping;

import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.model.Project;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {
    public static Project create(CreateProjectDto createProjectDto) {
        return Project.
                builder().
                id(createProjectDto.getId()).
                description(createProjectDto.getDescription()).
                name(createProjectDto.getName()).
                status(createProjectDto.getStatus()).
                build();
    }


    public static ProjectDto map(Project project) {
        return ProjectDto.
                builder().
                id(project.getId()).
                description(project.getDescription()).
                name(project.getName()).
                status(project.getStatus()).
                build();
    }

    public static List<ProjectDto> map(List<Project> list) {
        return list.stream().map(project ->
                        ProjectDto.
                                builder().
                                id(project.getId()).
                                description(project.getDescription()).
                                name(project.getName()).
                                status(project.getStatus()).
                                build()).
                collect(Collectors.toList());
    }
}
