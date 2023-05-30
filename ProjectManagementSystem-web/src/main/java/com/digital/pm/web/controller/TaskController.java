package com.digital.pm.web.controller;

import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.service.TaskService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/private/task",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDto> create(@RequestBody CreateTaskDto createTaskDto) {
        return ResponseEntity.ok(taskService.create(createTaskDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDto> update(@PathVariable("id") Long taskId, @RequestBody CreateTaskDto createTaskDto) {
        return ResponseEntity.ok(taskService.update(taskId, createTaskDto));
    }
    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<TaskDto> changeStatus(@PathVariable("id") Long taskId) {
        return ResponseEntity.ok(taskService.changeStatus(taskId));
    }


    //{
    //"statusList":[
    //    "CLOSED",
    //    "AT_WORK"
    //]
    //}
    @PostMapping("/find")
    public ResponseEntity<List<TaskDto>> findAll(@RequestBody TaskFilter taskFilter) {
        return ResponseEntity.ok(taskService.findAll(taskFilter));
    }

}
