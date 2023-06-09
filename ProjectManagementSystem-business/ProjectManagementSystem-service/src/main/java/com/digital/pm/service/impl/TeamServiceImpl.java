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
import com.digital.pm.service.mapping.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final EmployeeService employeeService;

    private final ProjectService projectService;

    private final Logger teamLogger;

    @Transactional
    @Override
    public TeamDto addEmployee(CreateTeamDto createTeamDto) {
        teamLogger.info("addEmployee method has started");

        //существует ли сотрудник с таким id
        if (!employeeService.existsById(createTeamDto.getEmployeeId())) {
            teamLogger.info("canceling the creation operation");
            throw invalidEmployeeId(createTeamDto.getEmployeeId());
        }
        //существует ли такой проект
        if (projectService.existsById(createTeamDto.getProjectId())) {
            teamLogger.info("canceling the creation operation");
            throw invalidProjectId(createTeamDto.getProjectId());
        }

        teamLogger.info(String.format("checking:is the employee %d involved in this project %d? ", createTeamDto.getEmployeeId(), createTeamDto.getProjectId()));

        //если сотрудник уже участвует в этом проекте ->ошибка
        if (teamRepository.existsByEmployeeIdAndProjectId(createTeamDto.getEmployeeId(), createTeamDto.getProjectId())) {
            teamLogger.info("canceling the creation operation");
            throw invalidEmployeeAlreadyInvolved(createTeamDto.getEmployeeId(), createTeamDto.getProjectId());
        }

        if (Objects.isNull(createTeamDto.getRole())) {
            teamLogger.info("canceling the creation operation");
            throw invalidRole();
        }
        var team = teamMapper.create(createTeamDto);

        teamLogger.info(String.format("the team %s is created", createTeamDto));

        teamRepository.save(team);

        teamLogger.info(String.format("the team %s has been saved ", createTeamDto));

        return teamMapper.map(team);
    }


    @Transactional
    @Override
    public TeamDto delete(Long employeeId, Long projectId) {
        teamLogger.info("delete method has started");

        teamLogger.info(String.format("find employee with %d id", employeeId));

        //существует ли такой пользователь
        if (!employeeService.existsById(employeeId)) {
            teamLogger.info("canceling the delete operation");

            throw invalidEmployeeId(employeeId);
        }
        teamLogger.info(String.format("find project with %d id", projectId));

        //существует ли такой проект
        if (projectService.existsById(projectId)) {
            teamLogger.info("canceling the delete operation");

            throw invalidProjectId(projectId);
        }
        //сотрудник участвует в проекте
        teamLogger.info(String.format("checking:is the employee %d involved in this project %d? ", employeeId, projectId));

        if (!teamRepository.existsByEmployeeIdAndProjectId(employeeId, projectId)) {
            teamLogger.info("canceling the delete operation");

            throw invalidEmployeeNotParticipate(employeeId, projectId);
        }
        var result = teamRepository.deleteByEmployeeIdAndProjectId(employeeId, projectId);

        teamLogger.info(String.format("the employee %d was removed from the team", employeeId));

        return teamMapper.map(result);
    }

    @Override
    public List<EmployeeDto> getAllByProjectId(Long projectId) {
        teamLogger.info("getAllByProjectId method has started");

        teamLogger.info(String.format("get all employees involved in the project %d", projectId));

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

