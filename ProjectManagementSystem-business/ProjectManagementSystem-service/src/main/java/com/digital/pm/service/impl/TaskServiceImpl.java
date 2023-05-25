package com.digital.pm.service.impl;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.repository.spec.TaskSpecification;
import com.digital.pm.repository.TaskRepository;
import com.digital.pm.service.TaskService;
import com.digital.pm.service.TeamService;
import com.digital.pm.service.mapping.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    private final TeamService teamService;

    @Override
    public ResponseEntity<?> create(CreateTaskDto createTaskDto) {
        if (createTaskDto.getDeadline().before(new Date(System.currentTimeMillis() + createTaskDto.getLaborCosts()))) {
            return ResponseEntity.
                    badRequest().
                    body("the end date cannot come earlier than " + new Date(System.currentTimeMillis() + createTaskDto.getLaborCosts()));
        }


        var task = taskMapper.create(createTaskDto);
        taskRepository.save(task);

        return ResponseEntity.ok(taskMapper.map(task));
    }

    @Override
    public ResponseEntity<?> update(Long taskId, CreateTaskDto createTaskDto) {
        if (!taskRepository.existsById(taskId)) {
            return ResponseEntity.badRequest().body(String.format("the task with %d id is not found", taskId));
        }

        var newTask = taskMapper.create(createTaskDto);
        newTask.setId(taskId);

        taskRepository.save(newTask);

        return ResponseEntity.ok(taskMapper.map(newTask));
    }

    @Override
    public ResponseEntity<?> findAll(TaskFilter taskFilter) {
        var result = taskRepository.
                findAll(TaskSpecification.getSpec(taskFilter));
        return ResponseEntity.ok(taskMapper.map(result));
    }

    @Override
    public ResponseEntity<?> changeStatus(Long taskId) {
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
        return ResponseEntity.ok(taskMapper.map(currentTask));

    }
}
