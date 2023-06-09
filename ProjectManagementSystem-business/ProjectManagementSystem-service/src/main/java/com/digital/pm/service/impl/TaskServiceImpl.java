package com.digital.pm.service.impl;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.model.Task;
import com.digital.pm.repository.spec.TaskSpecification;
import com.digital.pm.repository.TaskRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.TaskService;
import com.digital.pm.service.TeamService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    private final EmployeeService employeeService;

    private final TeamService teamService;

    private final Logger taskLogger;

    @Transactional
    @Override
    public TaskDto create(CreateTaskDto createTaskDto) {
        taskLogger.info("create method has started");

        //проверка обязательных полей
        if (!checkRequiredValues(createTaskDto)) {
            taskLogger.info("canceling the creating operation");
            throw invalidRequiredValues();
        }
        //проверка дедлайн>время на работу+тек время
        if (createTaskDto.getDeadline().before(new Date(System.currentTimeMillis() + createTaskDto.getLaborCost()))) {
            taskLogger.info("canceling the creating operation");
            throw invalidDeadline(createTaskDto);
        }

        //получим назначенного сотрудника
        var employeeDto = employeeService.getById(createTaskDto.getExecutorId());

        //проверка является ли сотрудник участником проекта
        if (!teamService.existsByEmployeeIdAndProjectId(employeeDto.getId(), createTaskDto.getProjectId())) {
            taskLogger.info("canceling the creating operation");
            throw invalidEmployeeNotAPartTeam(createTaskDto, employeeDto);
        }
        //проверка является ли тек автор участником проекта
        if (!teamService.existsByEmployeeIdAndProjectId(
                employeeService.findByAccount(SecurityContextHolder.getContext().
                                getAuthentication().
                                getName()).
                        getId(),
                createTaskDto.getProjectId())) {
            taskLogger.info("canceling the creating operation");
            throw invalidAuthorId(createTaskDto, employeeDto);
        }

        var task = taskMapper.create(createTaskDto);
        taskLogger.info(String.format("created task %s", createTaskDto));

        taskRepository.save(task);

        taskLogger.info(String.format("task %s has been saved", createTaskDto));


        return taskMapper.map(task);
    }

    @Transactional

    @Override
    public TaskDto update(Long taskId, CreateTaskDto createTaskDto) {
        taskLogger.info("update method has started");

        taskLogger.info(String.format("find task with %d id", taskId));

        var task = taskRepository.findById(taskId).orElseThrow(() -> invalidTaskId(taskId));
        taskLogger.info(String.format(" task with %d id is found", taskId));

        var newTask = taskMapper.update(task, createTaskDto);
        taskLogger.info(String.format("created new task %s", newTask));

        if (checkRequiredValues(newTask)) {
            taskLogger.info("canceling the update operation");
            throw invalidRequiredValues();
        }

        if (!employeeService.existsById(createTaskDto.getExecutorId())) {
            taskLogger.info("canceling the update operation");
            throw invalidEmployeeId(createTaskDto.getExecutorId());
        }

        newTask.setUpdateTime(new Date());
        taskLogger.info("set updateTime in task");


        taskRepository.save(newTask);
        taskLogger.info(String.format("new task has been saved %s", newTask));

        return taskMapper.map(newTask);
    }

    @Override
    public List<TaskDto> findAll(TaskFilter taskFilter) {
        taskLogger.info("findAll with filter method has started");

        taskLogger.info(String.format("find all task with filter %s", taskFilter));

        var result = taskRepository.
                findAll(TaskSpecification.getSpec(taskFilter));

        taskLogger.info("tasks successfully found");

        return taskMapper.map(result);
    }

    @Transactional
    public TaskDto changeStatus(Long taskId) {
        taskLogger.info("changeStatus method has started");

        taskLogger.info(String.format("find task with %d id", taskId));

        var currentTask = taskRepository.findById(taskId).orElseThrow(() -> invalidTaskId(taskId));

        switch (currentTask.getStatus().name().toUpperCase()) {

            case "CLOSED" -> {
                taskLogger.info("canceling the taskChangeStatus operation");

                throw new BadRequest("you cannot change the status for this task");
            }
            case "NEW" -> {
                currentTask.setStatus(TaskStatus.AT_WORK);
                taskLogger.info(String.format("the project statute has been changed from %s to %s", TaskStatus.NEW, currentTask.getStatus()));
            }
            case "AT_WORK" -> {
                currentTask.setStatus(TaskStatus.COMPLETED);
                taskLogger.info(String.format("the project statute has been changed from %s to %s", TaskStatus.AT_WORK, currentTask.getStatus()));
            }
            case "COMPLETED" -> {
                currentTask.setStatus(TaskStatus.CLOSED);
                taskLogger.info(String.format("the project statute has been changed from %s to %s", TaskStatus.COMPLETED, currentTask.getStatus()));
            }
        }

        taskRepository.save(currentTask);

        return taskMapper.map(taskRepository.save(currentTask));

    }

    public BadRequest invalidDeadline(CreateTaskDto createTaskDto) {
        return new BadRequest("the deadline cannot come earlier than " + new Date(System.currentTimeMillis() + createTaskDto.getLaborCost()));
    }

    public BadRequest invalidEmployeeNotAPartTeam(CreateTaskDto createTaskDto, EmployeeDto employeeDto) {
        return new BadRequest(String.format("the employee with id %s is not a member of team with %d", employeeDto.getId(), createTaskDto.getProjectId()));
    }

    public BadRequest invalidAuthorId(CreateTaskDto createTaskDto, EmployeeDto employeeDto) {
        return new BadRequest(String.format("the author of the task must be a participant in the project", employeeDto.getId(), createTaskDto.getProjectId()));

    }

    public BadRequest invalidRequiredValues() {
        return new BadRequest("task name,labor-cost and deadline fields are required");

    }

    public BadRequest invalidTaskId(Long id) {
        return new BadRequest(String.format("the task with %d id is not found", id));

    }

    public BadRequest invalidEmployeeId(Long id) {
        return new BadRequest(String.format("the employee with %d id is not found", id));
    }

    public boolean checkRequiredValues(CreateTaskDto createTaskDto) {
        taskLogger.info("checking required fields for a task");

        return Objects.nonNull(createTaskDto.getName()) &&
                !createTaskDto.getName().isBlank() &&
                Objects.nonNull(createTaskDto.getLaborCost()) &&
                Objects.nonNull(createTaskDto.getDeadline());
    }

    public boolean checkRequiredValues(Task newTask) {
        taskLogger.info("checking required fields for a task");

        return Objects.nonNull(newTask.getName()) &&
                !newTask.getName().isBlank() &&
                Objects.nonNull(newTask.getLaborCost()) &&
                Objects.nonNull(newTask.getDeadline());
    }
}
