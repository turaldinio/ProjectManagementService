package com.digital.pm.web.controller;

import com.digital.pm.common.filters.task.TaskDtoFilter;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.dto.taskFiles.CreateTaskFilesDto;
import com.digital.pm.dto.taskFiles.TaskFilesDto;
import com.digital.pm.service.TaskFileService;
import com.digital.pm.service.TaskService;
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
@Tag(name = "TaskController", description = "Контроллер задач")
@ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = TaskDto.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Неправильные параметры аргумента", content = @Content(schema = @Schema(), mediaType = "application/json"))
})

@RequestMapping("/private/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskFileService taskFileService;

    @Operation(summary = "создание задачи",
            description = "Создает задачу по указанным данным")

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> create(@RequestBody CreateTaskDto createTaskDto) {
        return ResponseEntity.ok(taskService.create(createTaskDto));
    }

    @Operation(summary = "обновление задачи",
            description = "Оьновляет задачу по указанному id")

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> update(@Parameter(description = "id задачи, которую обновляем")
                                          @PathVariable("id") Long taskId, @RequestBody CreateTaskDto createTaskDto) {
        return ResponseEntity.ok(taskService.update(taskId, createTaskDto));
    }

    @Operation(summary = "Изменение статуса задачи",
            description = "Переводит задачу в следующий статус")

    @PutMapping(value = "/changeStatus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> changeStatus(@Parameter(description = "id задачи, которую необходимо перевести в следующий статус")
                                                @PathVariable("id") Long taskId) {
        return ResponseEntity.ok(taskService.changeStatus(taskId));
    }


    //{
    //"statusList":[
    //    "CLOSED",
    //    "AT_WORK"
    //]
    //}
    @Operation(summary = "Поиск задачи по фильтрам",
            description = "Осуществляет поиск задачи по указанным фильтрам TaskDtoFilter, поля которого необязательны")

    @PostMapping(value = "/find", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> findAll(@RequestBody(required = false) TaskDtoFilter taskFilter) {
        return ResponseEntity.ok(taskService.findAll(taskFilter));
    }


    @Operation(summary = "Скачать файлы задачи",
            description = "Осуществляет поиск файлов для указанной задачи и скачивает их в zip формате")

    @GetMapping(value = "/file/download/{id}")
    public ResponseEntity<?> download(@Parameter(description = "id задачи, файлы которой необходимо загрузить")
                                           @PathVariable("id") Long id) {

        var file = taskFileService.downloadFile(id);//получаем архив файлов задачи

        if(Objects.isNull(file)){
           return ResponseEntity.ok(String.format("the task with id %d has no files to upload",id));
        }
        String downloadFileName = String.format("task_%d_files", id);
        return ResponseEntity.ok().
                header("Content-Disposition", String.format(" attachment; filename=%s.zip", downloadFileName)).
                contentLength(file.length()).
                body(taskFileService.getFileBytes(file));
    }

    @Operation(summary = "Сохранить файл для задачи",
            description = "Сохраняет файл для указанной задачи")

    @PostMapping(value = "/file/save/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskFilesDto> create(@Parameter(description = "id задачи, для которой сохраняем файл") @PathVariable("id") Long taskId,
                                               @RequestBody CreateTaskFilesDto taskFile) {
        var result = taskFileService.saveFile(taskFile, taskId);
        return ResponseEntity.ok(result);
    }

}
