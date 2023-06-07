package com.digital.pm.service.impl;

import com.digital.pm.common.enums.ProjectStatus;
import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.model.Project;
import com.digital.pm.repository.spec.ProjectSpecification;
import com.digital.pm.repository.ProjectRepository;
import com.digital.pm.service.ProjectService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    @Qualifier("projectLogger")
    private Logger logger;

    @Transactional
    @Override
    public ProjectDto create(CreateProjectDto createProjectDto) {
        logger.info("create method has started");

        if (!checkRequiredValue(createProjectDto)) {
            logger.info("canceling the creation operation");
            throw invalidRequiredValues();
        }
        if (projectRepository.existsByProjectCode(createProjectDto.getProjectCode())) {
            logger.info("canceling the creation operation");
            throw invalidProjectCode(createProjectDto.getProjectCode());
        }

        var project = projectMapper.create(createProjectDto);

        logger.info(String.format("project %s is created", createProjectDto));

        projectRepository.save(project);

        logger.info(String.format("project %s has been saved", createProjectDto));

        return projectMapper.map(project);
    }

    @Transactional
    @Override
    public ProjectDto update(Long projectId, CreateProjectDto createProjectDto) {
        logger.info("update method has started");

        logger.info(String.format("find project with %d id", projectId));

        var project = projectRepository.findById(projectId).orElseThrow(() -> {
                    logger.info("canceling the update operation");

                    return invalidId(projectId);
                }
        );

        var newProject = projectMapper.update(project, createProjectDto);

        logger.info(String.format("project %s has been updated", newProject));


        if (!checkRequiredValue(newProject)) {
            logger.info("canceling the update operation");

            throw invalidRequiredValues();

        }
        if (projectRepository.existsByProjectCode(newProject.getProjectCode())) {
            logger.info("canceling the update operation");
            throw invalidProjectCode(createProjectDto.getProjectCode());
        }
        projectRepository.save(newProject);
        logger.info(String.format("project %s has been saved", createProjectDto));

        return projectMapper.map(newProject);
    }

    @Transactional
    @Override
    public ProjectDto changeProjectStatus(Long projectId) {
        logger.info("changeProjectStatus method has started");

        logger.info(String.format("find project with %d id", projectId));
        var project = projectRepository.findById(projectId).orElseThrow(() -> invalidId(projectId));


        switch (project.getStatus().name().toUpperCase()) {
            case "COMPLETED" -> {
                logger.info("canceling the changeProjectStatus operation");
                throw new BadRequest("you cannot change the status for this project");
            }
            case "DRAFT" -> {
                project.setStatus(ProjectStatus.DEVELOPING);
                logger.info(String.format("the project statute has been changed from %s to %s", ProjectStatus.DRAFT, project.getStatus()));

            }
            case "DEVELOPING" -> {
                project.setStatus(ProjectStatus.TESTING);
                logger.info(String.format("the project statute has been changed from %s to %s", ProjectStatus.DEVELOPING, project.getStatus()));
            }
            case "TESTING" -> {
                project.setStatus(ProjectStatus.COMPLETED);
                logger.info(String.format("the project statute has been changed from %s to %s", ProjectStatus.TESTING, project.getStatus()));
            }
        }

        projectRepository.save(project);
        logger.info(String.format("project %s has been saved", project));

        return projectMapper.map(project);

    }

    @Override
    public List<ProjectDto> findAll(ProjectFilter projectFilter) {
        logger.info("findAll with filter method has started");

        logger.info(String.format("find all projects by filter %s", projectFilter));

        var result = projectRepository.
                findAll(ProjectSpecification.getSpec(projectFilter));

        logger.info("projects successfully found");

        return projectMapper.map(result);
    }

    @Override
    public Boolean existsById(Long id) {
        logger.info("existsById method has started");

        return projectRepository.existsById(id);
    }

    public BadRequest invalidRequiredValues() {
        return new BadRequest("project name and project code are required fields");
    }

    public BadRequest invalidId(Long id) {
        return new BadRequest(String.format("the project with %d id is not found", id));
    }

    public BadRequest invalidProjectCode(String code) {
        return new BadRequest(String.format("the %s code is already exists", code));

    }


    public boolean checkRequiredValue(CreateProjectDto createProjectDto) {
        logger.info("checking required fields for a project");

        return Objects.nonNull(createProjectDto.getProjectCode()) &&
                Objects.nonNull(createProjectDto.getName()) &&
                !createProjectDto.getProjectCode().isBlank() &&
                !createProjectDto.getName().isBlank();
    }

    public boolean checkRequiredValue(Project newProject) {
        logger.info("checking required fields for a project");

        return Objects.nonNull(newProject.getProjectCode()) &&
                Objects.nonNull(newProject.getName()) &&
                !newProject.getProjectCode().isBlank() &&
                !newProject.getName().isBlank();
    }
}

