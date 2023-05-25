package com.digital.pm.service;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import org.springframework.http.ResponseEntity;

public interface TaskService {
    ResponseEntity<?> create(CreateTaskDto createTaskDto);

    ResponseEntity<?> update(Long taskId, CreateTaskDto createTaskDto);

    ResponseEntity<?> findAll(TaskFilter taskFilter);

    ResponseEntity<?> changeStatus(Long taskId);
}
