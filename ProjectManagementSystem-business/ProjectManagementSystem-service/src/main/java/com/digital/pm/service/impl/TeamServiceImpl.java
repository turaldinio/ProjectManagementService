package com.digital.pm.service.impl;

import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import com.digital.pm.model.Team;
import com.digital.pm.repository.TeamRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.ProjectService;
import com.digital.pm.service.TeamService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.team.TeamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final EmployeeService employeeService;

    private final ProjectService projectService;


    @Transactional
    @Override
    public TeamDto addEmployee(CreateTeamDto createTeamDto) {
        log.info("addEmployee method has started");

        //существует ли сотрудник с таким id
        if (!employeeService.existsById(createTeamDto.getEmployeeId())) {
            log.info("canceling the creation operation");
            throw invalidEmployeeId(createTeamDto.getEmployeeId());
        }
        //существует ли такой проект
        if (!projectService.existsById(createTeamDto.getProjectId())) {
            log.info("canceling the creation operation");
            throw invalidProjectId(createTeamDto.getProjectId());
        }

        if (ObjectUtils.isEmpty(createTeamDto.getRole())) {//указана ли роль
            log.info("canceling the creation operation");
            throw invalidRole();
        }

        log.info(String.format("checking:is the employee %d involved in this project %d? ", createTeamDto.getEmployeeId(), createTeamDto.getProjectId()));

        //если сотрудник уже участвует в этом проекте ->ошибка
        if (teamRepository.existsByEmployeeIdAndProjectId(createTeamDto.getEmployeeId(), createTeamDto.getProjectId())) {
            log.info("canceling the creation operation");
            throw invalidEmployeeAlreadyInvolved(createTeamDto.getEmployeeId(), createTeamDto.getProjectId());
        }


        var team = teamMapper.create(createTeamDto);

        log.info(String.format("the team %s is created", createTeamDto));

        teamRepository.save(team);

        log.info(String.format("the team %s has been saved ", createTeamDto));

        return teamMapper.map(team);
    }


    @Transactional
    @Override
    public TeamDto delete(Long employeeId, Long projectId) {
        log.info("delete method has started");

        log.info(String.format("find employee with %d id", employeeId));

        //существует ли такой пользователь
        if (!employeeService.existsById(employeeId)) {
            log.info("canceling the delete operation");

            throw invalidEmployeeId(employeeId);
        }
        log.info(String.format("find project with %d id", projectId));

        //существует ли такой проект
        if (!projectService.existsById(projectId)) {
            log.info("canceling the delete operation");

            throw invalidProjectId(projectId);
        }
        log.info(String.format("checking:is the employee %d involved in this project %d? ", employeeId, projectId));

        var result = teamRepository.findByEmployeeIdAndProjectId(employeeId, projectId).orElseThrow(() -> {//участвует ли сотрудник в проекте
            log.info("canceling the delete operation");
            throw invalidEmployeeNotParticipate(employeeId, projectId);
        });

        teamRepository.removeTeamByEmployeeIdAndProjectId(employeeId, projectId);

        log.info(String.format("the employee %d was removed from the team", employeeId));

        return teamMapper.map(result);
    }

    @Override
    public List<EmployeeDto> getAllByProjectId(Long projectId) {
        log.info("getAllByProjectId method has started");

        log.info(String.format("get all employees involved in the project %d", projectId));

        return teamRepository.findAllByProjectId(projectId).
                stream().
                map(Team::getEmployeeId).
                map(employeeService::getById).
                filter(Objects::nonNull).
                toList();

    }

    @Override
    public Boolean existsByEmployeeIdAndProjectId(Long id, Long projectId) {
        return teamRepository.existsByEmployeeIdAndProjectId(id, projectId);
    }

    public BadRequest invalidEmployeeNotParticipate(Long employeeId, Long projectId) {
        return new BadRequest(String.format("the employee with %d is does not participate in the %d id project", employeeId, projectId));
    }

    public BadRequest invalidEmployeeAlreadyInvolved(Long employeeId, Long projectId) {
        return new BadRequest(String.format("employee %d is already involved in project %d", employeeId, projectId));
    }

    public BadRequest invalidEmployeeId(Long employeeId) {
        return new BadRequest(String.format("the employee with %d is not found", employeeId));
    }

    public BadRequest invalidProjectId(Long projectId) {
        return new BadRequest(String.format("the project with %d is not found", projectId));
    }

    private BadRequest invalidRole() {
        return new BadRequest("the Role filed cannot be null");
    }

}

