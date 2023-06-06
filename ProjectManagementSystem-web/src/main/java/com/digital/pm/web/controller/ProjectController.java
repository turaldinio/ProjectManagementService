package com.digital.pm.web.controller;

import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "ProjectController", description = "Контроллер проектов")
@ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ProjectDto.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Неправильные параметры аргумента", content = @Content(schema = @Schema(), mediaType = "application/json"))
})
@RequestMapping("/private/project")

public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "Создание проекта",
            description = "Создает проект по указанным данным")

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> create(@RequestBody CreateProjectDto createProjectDto) {
        return ResponseEntity.ok(projectService.create(createProjectDto));
    }

    @Operation(summary = "Обновление проекта",
            description = "Создает проект по указанным данным")

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> update(@PathVariable Long id, @RequestBody CreateProjectDto createProjectDto) {
        return ResponseEntity.ok(projectService.update(id, createProjectDto));
    }

    @Operation(summary = "Изменение статуса проекта по id",
            description = "Позволяет перевести проект в следующий статус")

    @PutMapping(value = "/change/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> changeStatus(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.changeProjectStatus(id));
    }

    @Operation(summary = "Поиск проекта по фильтрам",
            description = "Поиск осуществляется на основе переданного объекта ProjectFilter, поля которого необязательны к заполнению")

    @PostMapping(value = "/find", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<ProjectDto>> findAll(@RequestBody(required = false) ProjectFilter projectFilter) {
        return ResponseEntity.ok(projectService.findAll(projectFilter));
    }


}
