package com.digital.pm.service;

import com.digital.pm.common.filters.task.TaskDtoFilter;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto create(CreateTaskDto createTaskDto);

    TaskDto update(Long taskId, CreateTaskDto createTaskDto);

    List<TaskDto> findAll(TaskDtoFilter taskFilter);

    public TaskDto changeStatus(Long taskId);

}
