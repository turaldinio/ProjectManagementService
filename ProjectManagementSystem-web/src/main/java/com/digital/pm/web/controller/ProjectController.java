package com.digital.pm.web.controller;

import com.digital.pm.common.filters.project.ProjectDtoFilter;
import com.digital.pm.dto.project.CreateProjectDto;
import com.digital.pm.dto.project.ProjectDto;
import com.digital.pm.dto.projectFiles.CreateProjectFileDto;
import com.digital.pm.dto.projectFiles.ProjectFilesDto;
import com.digital.pm.service.ProjectFileService;
import com.digital.pm.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    private final ProjectFileService projectFileService;

    @Operation(summary = "Создание проекта",
            description = "Создает проект по указанным данным")

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> create(@RequestBody CreateProjectDto createProjectDto) {
        return ResponseEntity.ok(projectService.create(createProjectDto));
    }

    @Operation(summary = "Обновление проекта",
            description = "Создает проект по указанным данным")

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> update(@Parameter(description = "id проекта, который собираемся обновить")
                                             @PathVariable Long id, @RequestBody CreateProjectDto createProjectDto) {
        return ResponseEntity.ok(projectService.update(id, createProjectDto));
    }

    @Operation(summary = "Изменение статуса проекта по id",
            description = "Позволяет перевести проект в следующий статус")

    @PutMapping(value = "/change/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> changeStatus(@Parameter(description = "id проекта, который необходимо перевести в другой статус")
                                                   @PathVariable Long id) {
        return ResponseEntity.ok(projectService.changeProjectStatus(id));
    }

    @Operation(summary = "Поиск проекта по фильтрам",
            description = "Поиск осуществляется на основе переданного объекта ProjectDtoFilter, поля которого необязательны к заполнению")

    @PostMapping(value = "/find", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<ProjectDto>> findAll(@RequestBody(required = false) ProjectDtoFilter projectFilter) {
        return ResponseEntity.ok(projectService.findAll(projectFilter));
    }

    @Operation(summary = "Скачать файлы проекта",
            description = "Осуществляет поиск файлов для указанного проекта и скачивает их в zip формате")

    @GetMapping(value = "/file/download/{id}")

    public ResponseEntity<?> download(@Parameter(description = "id проекта, файлы которой необходимо загрузить")
                                      @PathVariable("id") Long id) {

        var file = projectFileService.downloadFile(id);//получаем архив файлов проекта

        if (Objects.isNull(file)) {
            return ResponseEntity.ok(String.format("the project with id %d has no files to upload", id));
        }
        String downloadFileName = String.format("project_%d_files", id);
        return ResponseEntity.ok().
                header("Content-Disposition", String.format(" attachment; filename=%s.zip", downloadFileName)).
                contentLength(file.length()).
                body(projectFileService.getFileBytes(file));
    }

    @Operation(summary = "Сохранить файл для проекта",
            description = "Сохраняет файл для указанного проекта")

    @PostMapping(value = "/file/save/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectFilesDto> create(@Parameter(description = "id проекта, для которой сохраняем файл") @PathVariable("id") Long taskId,
                                                  @RequestBody CreateProjectFileDto projectFileDto) {
        var result = projectFileService.saveFile(projectFileDto, taskId);
        return ResponseEntity.ok(result);
    }


}
