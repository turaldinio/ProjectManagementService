package com.digital.pm.web.controller;

import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateTaskDto createTaskDto) {
        return taskService.create(createTaskDto);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long taskId, @RequestBody CreateTaskDto createTaskDto) {
        return taskService.update(taskId, createTaskDto);
    }

    @PostMapping("/find")
    public ResponseEntity<?>findAll(TaskFilter taskFilter){
        return taskService.findAll(taskFilter);
    }

}
