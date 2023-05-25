package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.model.task.Task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {
    // TODO: 25.05.2023 дефолтное значение в authorId
    public Task create(CreateTaskDto createTaskDto) {
        return Task.builder().
                name(createTaskDto.getName()).
                description(createTaskDto.getDescription()).
                executorId(createTaskDto.getExecutorId()).
                laborCost(createTaskDto.getLaborCost()).
                deadline(createTaskDto.getDeadline()).
                status(TaskStatus.NEW).
                dateOfCreation(new Date()).
                authorId(1L).
                projectId(createTaskDto.getProjectId()).
                build();
    }

    public TaskDto map(Task task) {
        return TaskDto.
                builder().
                id(task.getId()).
                name(task.getName()).
                description(task.getDescription()).
                executorId(task.getExecutorId()).
                authorId(task.getAuthorId()).
                projectId(task.getProjectId()).
                laborCosts(task.getLaborCost()).
                deadline(task.getDeadline()).
                status(task.getStatus()).
                dateOfCreation(task.getDateOfCreation()).
                updateTime(task.getUpdateTime()).
                build();

    }

    public List<TaskDto> map(List<Task> list) {
        return list.
                stream().
                map(x -> TaskDto.
                        builder().
                        id(x.getId()).
                        name(x.getName()).
                        description(x.getDescription()).
                        executorId(x.getExecutorId()).
                        authorId(x.getAuthorId()).
                        projectId(x.getProjectId()).
                        laborCosts(x.getLaborCost()).
                        deadline(x.getDeadline()).
                        status(x.getStatus()).
                        dateOfCreation(x.getDateOfCreation()).
                        updateTime(x.getUpdateTime()).
                        build()).
                collect(Collectors.toList());

    }
}
