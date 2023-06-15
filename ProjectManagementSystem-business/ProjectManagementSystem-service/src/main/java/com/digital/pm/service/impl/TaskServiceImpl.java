package com.digital.pm.service.impl;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.common.filters.task.TaskDtoFilter;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.model.Task;
import com.digital.pm.repository.spec.TaskSpecification;
import com.digital.pm.repository.TaskRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.ProjectService;
import com.digital.pm.service.TaskService;
import com.digital.pm.service.TeamService;
import com.digital.pm.service.amqp.MessageProduce;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.task.TaskFilterMapping;
import com.digital.pm.service.mapping.task.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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

    private final ProjectService projectService;


    @Transactional
    @Override
    public TaskDto create(CreateTaskDto createTaskDto) {
        log.info("create method has started");

        //проверка обязательных полей
        if (checkRequiredValues(createTaskDto)) {
            log.info("canceling the creating operation");
            throw invalidRequiredValues();
        }

        if (checkLaborCost(createTaskDto.getLaborCost())) {//laborcost > 0?
            log.info("canceling the creating operation");
            throw invalidLaborCost();
        }
        //проверка дедлайн>время на работу+тек время
        if (checkDeadLineValues(createTaskDto)) {
            log.info("canceling the creating operation");
            throw invalidDeadline(createTaskDto.getLaborCost());
        }
        if (!projectService.existsById(createTaskDto.getProjectId())) {//существует ли переданный проект (projectId)?
            log.info("canceling the creating operation");
            throw invalidProjectId(createTaskDto.getProjectId());
        }

        if (!ObjectUtils.isEmpty(createTaskDto.getExecutorId())) {
            //получим назначенного сотрудника
            var employeeDto = employeeService.getById(createTaskDto.getExecutorId());

            //проверка является ли сотрудник участником проекта
            if (!teamService.existsByEmployeeIdAndProjectId(employeeDto.getId(), createTaskDto.getProjectId())) {
                log.info("canceling the creating operation");
                throw invalidEmployeeNotAPartTeam(createTaskDto, employeeDto);
            }

            if (employeeDto.getStatus().equals(EmployeeStatus.REMOTE)) {//проверка статуса исполнителя задачи
                log.info("canceling the creating operation");
                throw invalidEmployeeStatus();
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

        if (!ObjectUtils.isEmpty(createTaskDto.getExecutorId())) {
            var employeeDto = employeeService.getById(createTaskDto.getExecutorId());
            if (ObjectUtils.isEmpty(employeeDto.getEmail())) {
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

        if (!ObjectUtils.isEmpty(createTaskDto.getExecutorId())) {//проверим, существует ли указанный employeeId
            var executor = employeeService.getById(createTaskDto.getExecutorId());
            if (executor.getStatus().equals(EmployeeStatus.REMOTE)) {//проверим статус employee
                throw invalidEmployeeStatus();
            }
        }

        if (!ObjectUtils.isEmpty(createTaskDto.getProjectId())) {//проверим, существует ли указанный projectId
            if (!projectService.existsById(createTaskDto.getProjectId())) {
                log.info("canceling the update operation");
                throw invalidProjectId(createTaskDto.getProjectId());
            }
        }

        var newTask = taskMapper.update(task, createTaskDto);
        log.info(String.format("created new task %s", newTask));

        if (checkRequiredValues(newTask)) {//проверка обязательных полей
            log.info("canceling the update operation");
            throw invalidRequiredValues();
        }
        if (checkLaborCost(newTask.getLaborCost())) {//laborcost > 0?
            log.info("canceling the creating operation");
            throw invalidLaborCost();
        }

        if (checkDeadLineValues(newTask)) {
            log.info("canceling the update operation");
            throw invalidDeadline(newTask.getLaborCost());
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
        if (ObjectUtils.isEmpty(taskFilter)) {
            return taskMapper.map(taskRepository.findAll());
        }

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

    public BadRequest invalidDeadline(Long laborCost) {
        return new BadRequest("the deadline cannot come earlier than " + new Date(System.currentTimeMillis() + laborCost * 60 * 60 * 1000));
    }

    public BadRequest invalidEmployeeStatus() {
        return new BadRequest("the task executor cannot be in the REMOTE status");
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

    public BadRequest invalidProjectId(Long id) {
        return new BadRequest(String.format("the project with %d id is not found", id));

    }

    public BadRequest invalidEmployeeId(Long id) {
        return new BadRequest(String.format("the employee with %d id is not found", id));
    }

    public BadRequest invalidLaborCost() {
        return new BadRequest("the laborCost cannot be < 0");
    }

    public boolean checkRequiredValues(CreateTaskDto createTaskDto) {
        log.info("checking required fields for a task");
        return ObjectUtils.isEmpty(createTaskDto.getName()) ||
                ObjectUtils.isEmpty(createTaskDto.getLaborCost()) ||
                ObjectUtils.isEmpty(createTaskDto.getProjectId()) ||
                ObjectUtils.isEmpty(createTaskDto.getDeadline());

    }

    public boolean checkRequiredValues(Task newTask) {
        log.info("checking required fields for a task");

        return ObjectUtils.isEmpty(newTask.getName()) ||
                ObjectUtils.isEmpty(newTask.getLaborCost()) ||
                ObjectUtils.isEmpty(newTask.getProjectId()) ||
                ObjectUtils.isEmpty(newTask.getDeadline());

    }

    public boolean checkLaborCost(Long laborCost) {
        return laborCost < 0;
    }


    public boolean checkDeadLineValues(CreateTaskDto createTaskDto) {
        return !createTaskDto.getDeadline().after(new Date(System.currentTimeMillis() + createTaskDto.getLaborCost() * 60 * 60 * 1000));
    }

    private boolean checkDeadLineValues(Task newTask) {
        return !newTask.getDeadline().after(new Date(System.currentTimeMillis() + newTask.getLaborCost() * 60 * 60 * 1000));

    }


}
