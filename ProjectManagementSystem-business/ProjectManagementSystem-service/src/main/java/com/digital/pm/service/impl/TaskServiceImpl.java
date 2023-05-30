package com.digital.pm.service.impl;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.task.CreateTaskDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.model.task.Task;
import com.digital.pm.repository.spec.TaskSpecification;
import com.digital.pm.repository.TaskRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.TaskService;
import com.digital.pm.service.TeamService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    private final EmployeeService employeeService;

    private final TeamService teamService;

    @Override
    public TaskDto create(CreateTaskDto createTaskDto) {
        //проверка обязательных полей
        if (!checkRequiredValues(createTaskDto)) {
            throw invalidRequiredValues();
        }
        //проверка дедлайн>время на работу+тек время
        if (createTaskDto.getDeadline().before(new Date(System.currentTimeMillis() + createTaskDto.getLaborCost()))) {
            throw invalidDeadline(createTaskDto);
        }

        //получим назначенного сотрудника
        var employeeDto = employeeService.getById(createTaskDto.getExecutorId());

        //проверка является ли сотрудник участником проекта
        if (!teamService.existsByEmployeeIdAndProjectId(employeeDto.getId(), createTaskDto.getProjectId())) {
            throw invalidEmployeeNotAPartTeam(createTaskDto, employeeDto);
        }
        //проверка является ли тек автор участником проекта
        if (!teamService.existsByEmployeeIdAndProjectId(
                employeeService.findByAccount(SecurityContextHolder.getContext().
                                getAuthentication().
                                getName()).
                        getId(),
                createTaskDto.getProjectId())) {
            throw invalidAuthorId(createTaskDto, employeeDto);
        }

        var task = taskMapper.create(createTaskDto);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    @Override
    public TaskDto update(Long taskId, CreateTaskDto createTaskDto) {
        var task = taskRepository.findById(taskId).orElseThrow(() -> invalidTaskId(taskId));
        var newTask = taskMapper.update(task, createTaskDto);

        if (checkRequiredValues(newTask)) {
            throw invalidRequiredValues();
        }

        if (!employeeService.existsById(createTaskDto.getExecutorId())) {
            throw invalidEmployeeId(createTaskDto.getExecutorId());
        }

        newTask.setUpdateTime(new Date());

        taskRepository.save(newTask);

        return taskMapper.map(newTask);
    }

    @Override
    public List<TaskDto> findAll(TaskFilter taskFilter) {

        // TODO: 25.05.2023 вопрос про поиск по дате.искать точное совпадение или "раньше чем". Дописать сортировку
        var result = taskRepository.
                findAll(TaskSpecification.getSpec(taskFilter));
        return taskMapper.map(result);
    }

    public TaskDto changeStatus(Long taskId) {
        var currentTask = taskRepository.findById(taskId).orElseThrow(() -> invalidTaskId(taskId));
        if (currentTask.getStatus().equals(TaskStatus.CLOSED)) {
            throw new BadRequest("you cannot change the status for this task");
        }

        if (currentTask.getStatus().equals(TaskStatus.NEW)) {
            currentTask.setStatus(TaskStatus.AT_WORK);
        }
        if (currentTask.getStatus().equals(TaskStatus.AT_WORK)) {
            currentTask.setStatus(TaskStatus.COMPLETED);
        }
        if (currentTask.getStatus().equals(TaskStatus.COMPLETED)) {
            currentTask.setStatus(TaskStatus.CLOSED);
        }

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
        return createTaskDto.getName() != null &&
                !createTaskDto.getName().isBlank() &&
                createTaskDto.getLaborCost() != null &&
                createTaskDto.getDeadline() != null;
    }

    public boolean checkRequiredValues(Task newTask) {
        return newTask.getName() != null &&
                !newTask.getName().isBlank() &&
                newTask.getLaborCost() != null &&
                newTask.getDeadline() != null;
    }
}
