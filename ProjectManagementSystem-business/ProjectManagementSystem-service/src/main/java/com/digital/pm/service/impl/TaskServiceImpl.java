package com.digital.pm.service.impl;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.common.filters.task.TaskDtoFilter;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.model.Task;
import com.digital.pm.repository.spec.TaskSpecification;
import com.digital.pm.repository.TaskRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.TaskService;
import com.digital.pm.service.TeamService;
import com.digital.pm.service.amqp.MessageProduce;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.TaskFilterMapping;
import com.digital.pm.service.mapping.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    private final EmployeeService employeeService;

    private final TeamService teamService;
    private final MessageProduce messageProduce;

    private final TaskFilterMapping taskFilterMapping;


    @Transactional
    @Override
    public TaskDto create(CreateTaskDto createTaskDto) {
        log.info("create method has started");

        //проверка обязательных полей
        if (!checkRequiredValues(createTaskDto)) {
            log.info("canceling the creating operation");
            throw invalidRequiredValues();
        }
        //проверка дедлайн>время на работу+тек время
        if (createTaskDto.getDeadline().before(new Date(System.currentTimeMillis() + createTaskDto.getLaborCost()))) {
            log.info("canceling the creating operation");
            throw invalidDeadline(createTaskDto);
        }

        if (Objects.nonNull(createTaskDto.getExecutorId())) {
            //получим назначенного сотрудника
            var employeeDto = employeeService.getById(createTaskDto.getExecutorId());

            //проверка является ли сотрудник участником проекта
            if (!teamService.existsByEmployeeIdAndProjectId(employeeDto.getId(), createTaskDto.getProjectId())) {
                log.info("canceling the creating operation");
                throw invalidEmployeeNotAPartTeam(createTaskDto, employeeDto);
            }

        }

        //проверка является ли тек автор участником проекта
        if (!teamService.existsByEmployeeIdAndProjectId(employeeService.
                        findByAccount(SecurityContextHolder.
                                getContext().
                                getAuthentication().
                                getName()).
                        getId(),
                createTaskDto.getProjectId())) {
            log.info("canceling the creating operation");
            throw invalidAuthorId();
        }

        var task = taskMapper.create(createTaskDto);
        log.info(String.format("created task %s", createTaskDto));

        var taskResult = taskRepository.save(task);
        log.info(String.format("task %s has been saved", createTaskDto));

        if (Objects.nonNull(createTaskDto.getExecutorId())) {
            var employeeDto = employeeService.getById(createTaskDto.getExecutorId());
            if (Objects.nonNull(employeeDto.getEmail())) {
                messageProduce.notifyAnEmployee(taskResult.getId(), employeeDto.getEmail());
            }
        }
        return taskMapper.map(task);
    }

    @Transactional
    @Override
    public TaskDto update(Long taskId, CreateTaskDto createTaskDto) {
        log.info("update method has started");

        log.info(String.format("find task with %d id", taskId));

        var task = taskRepository.findById(taskId).orElseThrow(() -> invalidTaskId(taskId));
        log.info(String.format(" task with %d id is found", taskId));

        var newTask = taskMapper.update(task, createTaskDto);
        log.info(String.format("created new task %s", newTask));

        if (checkRequiredValues(newTask)) {
            log.info("canceling the update operation");
            throw invalidRequiredValues();
        }

        if (!employeeService.existsById(createTaskDto.getExecutorId())) {
            log.info("canceling the update operation");
            throw invalidEmployeeId(createTaskDto.getExecutorId());
        }

        newTask.setUpdateTime(new Date());
        log.info("set updateTime in task");


        taskRepository.save(newTask);
        log.info(String.format("new task has been saved %s", newTask));

        return taskMapper.map(newTask);
    }

    @Override
    public List<TaskDto> findAll(TaskDtoFilter taskFilter) {
        log.info("findAll with filter method has started");

        log.info("mapping TaskDtoFilter to TaskDaoFilter");

        var taskDaoFilter = taskFilterMapping.create(taskFilter);

        log.info(String.format("find all task with filter %s", taskDaoFilter));

        var result = taskRepository.
                findAll(TaskSpecification.getSpec(taskDaoFilter));

        log.info("tasks successfully found");

        return taskMapper.map(result);
    }

    @Transactional
    public TaskDto changeStatus(Long taskId) {
        log.info("changeStatus method has started");

        log.info(String.format("find task with %d id", taskId));

        var currentTask = taskRepository.findById(taskId).orElseThrow(() -> invalidTaskId(taskId));

        switch (currentTask.getStatus()) {

            case CLOSED -> {
                log.info("canceling the taskChangeStatus operation");

                throw new BadRequest("you cannot change the status for this task");
            }
            case NEW -> {
                currentTask.setStatus(TaskStatus.AT_WORK);
                log.info(String.format("the project statute has been changed from %s to %s", TaskStatus.NEW, currentTask.getStatus()));
            }
            case AT_WORK -> {
                currentTask.setStatus(TaskStatus.COMPLETED);
                log.info(String.format("the project statute has been changed from %s to %s", TaskStatus.AT_WORK, currentTask.getStatus()));
            }
            case COMPLETED -> {
                currentTask.setStatus(TaskStatus.CLOSED);
                log.info(String.format("the project statute has been changed from %s to %s", TaskStatus.COMPLETED, currentTask.getStatus()));
            }
        }

        taskRepository.save(currentTask);

        return taskMapper.map(taskRepository.save(currentTask));

    }

    @Override
    public Boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }

    public BadRequest invalidDeadline(CreateTaskDto createTaskDto) {
        return new BadRequest("the deadline cannot come earlier than " + new Date(System.currentTimeMillis() + createTaskDto.getLaborCost()));
    }

    public BadRequest invalidEmployeeNotAPartTeam(CreateTaskDto createTaskDto, EmployeeDto employeeDto) {
        return new BadRequest(String.format("the employee with id %s is not a member of team with %d", employeeDto.getId(), createTaskDto.getProjectId()));
    }

    public BadRequest invalidAuthorId() {
        return new BadRequest("the author of the task must be a participant in the project");

    }

    public BadRequest invalidRequiredValues() {
        return new BadRequest("task name/laborCost/deadline/projectId fields are required");

    }

    public BadRequest invalidTaskId(Long id) {
        return new BadRequest(String.format("the task with %d id is not found", id));

    }

    public BadRequest invalidEmployeeId(Long id) {
        return new BadRequest(String.format("the employee with %d id is not found", id));
    }

    public boolean checkRequiredValues(CreateTaskDto createTaskDto) {
        log.info("checking required fields for a task");

        return Objects.nonNull(createTaskDto.getName()) &&
                !createTaskDto.getName().isBlank() &&
                Objects.nonNull(createTaskDto.getLaborCost()) &&
                Objects.nonNull(createTaskDto.getProjectId()) &&
                Objects.nonNull(createTaskDto.getDeadline());
    }

    public boolean checkRequiredValues(Task newTask) {
        log.info("checking required fields for a task");

        return Objects.nonNull(newTask.getName()) &&
                !newTask.getName().isBlank() &&
                Objects.nonNull(newTask.getLaborCost()) &&
                Objects.nonNull(newTask.getDeadline());
    }
}
