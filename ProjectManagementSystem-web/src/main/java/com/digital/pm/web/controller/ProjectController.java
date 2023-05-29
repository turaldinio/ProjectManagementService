package com.digital.pm.web.controller;

import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/private/project",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> create(@RequestBody CreateProjectDto createProjectDto) {
        return ResponseEntity.ok(projectService.create(createProjectDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectDto> update(@PathVariable Long id, @RequestBody CreateProjectDto createProjectDto) {
        return ResponseEntity.ok(projectService.update(id, createProjectDto));
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<ProjectDto> changeStatus(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.changeProjectStatus(id));
    }

    @PostMapping("/find")
    private ResponseEntity<List<ProjectDto>> findAll(@RequestBody ProjectFilter projectFilter) {
        return ResponseEntity.ok(projectService.findAll(projectFilter));
    }


}
