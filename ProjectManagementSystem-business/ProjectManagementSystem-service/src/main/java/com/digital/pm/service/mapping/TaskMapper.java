package com.digital.pm.service.mapping;

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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TaskMapper {
    private final EmployeeService employeeService;

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
        if (createTaskDto.getName() != null) {
            task.setName(createTaskDto.getName());
        }
        if (createTaskDto.getDeadline() != null) {
            task.setDeadline(createTaskDto.getDeadline());
        }
        if (createTaskDto.getExecutorId() != null) {
            task.setExecutorId(createTaskDto.getExecutorId());
        }
        if (createTaskDto.getDescription() != null) {
            task.setDescription(createTaskDto.getDescription());
        }
        if (createTaskDto.getLaborCost() != null) {
            task.setLaborCost(createTaskDto.getLaborCost());
        }
        if (createTaskDto.getProjectId() != null) {
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
                        creationDate(x.getCreationDate()).
                        updateTime(x.getUpdateTime()).
                        build()).
                collect(Collectors.toList());

    }
}
