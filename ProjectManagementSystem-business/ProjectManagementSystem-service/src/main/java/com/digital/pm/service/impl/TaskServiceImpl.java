package com.digital.pm.service.impl;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.model.task.TaskSpecification;
import com.digital.pm.repository.TaskRepository;
import com.digital.pm.service.TaskService;
import com.digital.pm.service.mapping.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public TaskDto create(CreateTaskDto createTaskDto) {
        var task = TaskMapper.create(createTaskDto);
        taskRepository.save(task);

        return TaskMapper.map(task);
    }

    @Override
    public TaskDto update(Long taskId, CreateTaskDto createTaskDto) {
        var currentTask = taskRepository.findById(taskId).orElseThrow();
        currentTask = TaskMapper.create(createTaskDto);

        taskRepository.save(currentTask);

        return TaskMapper.map(currentTask);
    }

    @Override
    public TaskDto findOne(TaskFilter taskFilter) {
        var result = taskRepository.
                findOne(TaskSpecification.getSpec(taskFilter)).
                orElseThrow();
        return TaskMapper.map(result);
    }

    @Override
    public TaskDto changeStatus(Long taskId) {
        var currentTask = taskRepository.findById(taskId).orElseThrow();

        if (currentTask.getStatus().equals(TaskStatus.NEW)) {
            currentTask.setStatus(TaskStatus.AT_WORK);
        }
        if (currentTask.getStatus().equals(TaskStatus.AT_WORK)) {
            currentTask.setStatus(TaskStatus.COMPLETED);
        }
        if (currentTask.getStatus().equals(TaskStatus.COMPLETED)) {
            currentTask.setStatus(TaskStatus.CLOSED);
        }
        return TaskMapper.map(currentTask);

    }
}
