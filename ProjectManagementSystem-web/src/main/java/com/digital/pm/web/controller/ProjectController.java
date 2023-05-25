package com.digital.pm.web.controller;

import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateProjectDto createProjectDto) {
        return projectService.create(createProjectDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CreateProjectDto createProjectDto) {
        return projectService.update(id, createProjectDto);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<?>changeStatus(@PathVariable Long id){
        return projectService.changeProjectStatus(id);
    }

    @PostMapping("/find")
    private ResponseEntity<?> findAll(@RequestBody ProjectFilter projectFilter) {
        return projectService.findAll(projectFilter);
    }


}
