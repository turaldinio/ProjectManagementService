package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.ProjectStatus;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.model.project.Project;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ProjectMapper {
    public  Project create(CreateProjectDto createProjectDto) {
        return Project.
                builder().
                description(createProjectDto.getDescription()).
                name(createProjectDto.getName()).
                status(ProjectStatus.DRAFT).
                build();
    }


    public  ProjectDto map(Project project) {
        return ProjectDto.
                builder().
                id(project.getId()).
                description(project.getDescription()).
                name(project.getName()).
                status(project.getStatus()).
                build();
    }

    public  List<ProjectDto> map(List<Project> list) {
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
