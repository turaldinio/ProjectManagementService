package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.model.task.Task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {
    // TODO: 21.05.2023 executor какой объект положить в него?
    public static Task create(CreateTaskDto createTaskDto) {
        return Task.builder().
                name(createTaskDto.getName()).
                description(createTaskDto.getDescription()).
                executor(null).
                laborCosts(createTaskDto.getLaborCosts()).
                deadline(createTaskDto.getDeadline()).
                status(TaskStatus.NEW).
                dateOfCreation(new Date()).
                updateTime(createTaskDto.getUpdateTime()).
                author(createTaskDto.getAuthor()).
                build();
    }

    public static TaskDto map(Task task) {
        return TaskDto.
                builder().
                id(task.getId()).
                name(task.getName()).
                description(task.getDescription()).
                executor(null).
                laborCosts(task.getLaborCosts()).
                deadline(task.getDeadline()).
                status(task.getStatus()).
                dateOfCreation(task.getDateOfCreation()).
                updateTime(task.getUpdateTime()).
                author(task.getAuthor()).
                build();

    }

    public static List<TaskDto> map(List<Task> list) {
        return list.stream().map(x ->
                        TaskDto.builder().
                                id(x.getId()).
                                name(x.getName()).
                                description(x.getDescription()).
                                executor(null).
                                laborCosts(x.getLaborCosts()).
                                deadline(x.getDeadline()).
                                status(x.getStatus()).
                                dateOfCreation(x.getDateOfCreation()).
                                updateTime(x.getUpdateTime()).
                                author(x.getAuthor()).
                                build()).
                collect(Collectors.toList());

    }
}
