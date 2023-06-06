package com.digital.pm.web.controller;

import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.service.TaskService;
import com.google.gson.Gson;
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

    @Operation(summary = "создание задачи",
            description = "Создает задачу по указанным данным")

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> create(@RequestBody CreateTaskDto createTaskDto) {
        return ResponseEntity.ok(taskService.create(createTaskDto));
    }

    @Operation(summary = "обновление задачи",
            description = "Оьновляет задачу по указанному id")

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> update(@PathVariable("id") Long taskId, @RequestBody CreateTaskDto createTaskDto) {
        return ResponseEntity.ok(taskService.update(taskId, createTaskDto));
    }

    @Operation(summary = "Изменение статуса задачи",
            description = "Переводит задачу в следующий статус")

    @PutMapping(value = "/changeStatus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> changeStatus(@PathVariable("id") Long taskId) {
        return ResponseEntity.ok(taskService.changeStatus(taskId));
    }


    //{
    //"statusList":[
    //    "CLOSED",
    //    "AT_WORK"
    //]
    //}
    @Operation(summary = "Поиск задачи по фильтрам",
            description = "Осуществляет поиск задачи по указанным фильтрам TaskFilter, поля которого необязательны")

    @PostMapping(value = "/find", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> findAll(@RequestBody(required = false) TaskFilter taskFilter) {
        return ResponseEntity.ok(taskService.findAll(taskFilter));
    }

}
