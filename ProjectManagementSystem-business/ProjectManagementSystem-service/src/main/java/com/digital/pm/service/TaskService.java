package com.digital.pm.service;

import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskService {
    TaskDto create(CreateTaskDto createTaskDto);

    TaskDto update(Long taskId, CreateTaskDto createTaskDto);

    List<TaskDto> findAll(TaskFilter taskFilter);

    public TaskDto changeStatus(Long taskId);

}
