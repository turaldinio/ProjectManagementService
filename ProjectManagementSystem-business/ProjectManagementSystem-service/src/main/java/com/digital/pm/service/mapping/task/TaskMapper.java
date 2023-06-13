package com.digital.pm.service.mapping.task;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.model.Task;
import com.digital.pm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TaskMapper {
    private final EmployeeService employeeService;
    private final TaskFileMapping taskFileMapping;


    public Task create(CreateTaskDto createTaskDto) {
        return Task.builder().
                name(createTaskDto.getName()).
                description(createTaskDto.getDescription()).
                executorId(createTaskDto.getExecutorId()).
                laborCost(createTaskDto.getLaborCost()).
                deadline(createTaskDto.getDeadline()).
                status(TaskStatus.NEW).
                creationDate(new Date()).
                authorId(employeeService.
                        findByAccount(SecurityContextHolder.
                                getContext().
                                getAuthentication().
                                getName())
                        .getId()).
                projectId(createTaskDto.getProjectId()).
                build();
    }

    public Task update(Task task, CreateTaskDto createTaskDto) {
        if (Objects.nonNull(createTaskDto.getName())) {
            task.setName(createTaskDto.getName());
        }
        if (Objects.nonNull(createTaskDto.getDeadline())) {
            task.setDeadline(createTaskDto.getDeadline());
        }
        if (Objects.nonNull(createTaskDto.getExecutorId())) {
            task.setExecutorId(createTaskDto.getExecutorId());
        }
        if (Objects.nonNull(createTaskDto.getDescription())) {
            task.setDescription(createTaskDto.getDescription());
        }
        if (Objects.nonNull(createTaskDto.getLaborCost())) {
            task.setLaborCost(createTaskDto.getLaborCost());
        }
        if (Objects.nonNull(createTaskDto.getProjectId())) {
            task.setProjectId(createTaskDto.getProjectId());
        }
        return task;
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
                creationDate(task.getCreationDate()).
                updateTime(task.getUpdateTime()).
                taskFilesDto(taskFileMapping.map(task.getFiles())).
                build();

    }

    public List<TaskDto> map(List<Task> list) {
        return list.
                stream().
                map(this::map).collect(Collectors.toList());

    }
}
