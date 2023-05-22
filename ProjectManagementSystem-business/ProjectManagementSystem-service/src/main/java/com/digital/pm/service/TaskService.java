package com.digital.pm.service;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;

public interface TaskService {
    TaskDto create(CreateTaskDto createTaskDto);

    TaskDto update(Long taskId, CreateTaskDto createTaskDto);

    TaskDto findOne(TaskFilter taskFilter);

    TaskDto changeStatus(Long taskId);
}
