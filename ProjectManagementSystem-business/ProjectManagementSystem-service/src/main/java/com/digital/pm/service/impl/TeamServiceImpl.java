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

    @Transactional
    @Override
    public TeamDto addEmployee(CreateTeamDto createTeamDto) {
        //существует ли сотрудник с таким id
        if (!employeeService.existsById(createTeamDto.getEmployeeId())) {
            throw invalidEmployeeId(createTeamDto.getEmployeeId());
        }
        //существует ли такой проект
        if (projectService.existsById(createTeamDto.getProjectId())) {
            throw invalidProjectId(createTeamDto.getProjectId());
        }

        //если сотрудник уже участвует в этом проекте ->ошибка
        if (teamRepository.existsByEmployeeIdAndProjectId(createTeamDto.getEmployeeId(), createTeamDto.getProjectId())) {
            throw invalidEmployeeAlreadyInvolved(createTeamDto.getEmployeeId(), createTeamDto.getProjectId());
        }

        if (Objects.isNull(createTeamDto.getRole())) {
            throw invalidRole();
        }
        var team = teamMapper.create(createTeamDto);

        teamRepository.save(team);

        return teamMapper.map(team);
    }

    private BadRequest invalidRole() {
        return new BadRequest("the Role filed cannot be null");
    }

    @Transactional
    @Override
    public TeamDto delete(Long employeeId, Long projectId) {
        //существует ли такой пользователь
        if (!employeeService.existsById(employeeId)) {
            throw invalidEmployeeId(employeeId);
        }
        //существует ли такой проект
        if (projectService.existsById(projectId)) {
            throw invalidProjectId(projectId);
        }
        if (!teamRepository.existsByEmployeeIdAndProjectId(employeeId, projectId)) {
            throw invalidEmployeeNotParticipate(employeeId, projectId);
        }
        var result = teamRepository.deleteByEmployeeIdAndProjectId(employeeId, projectId);

        return teamMapper.map(result);
    }

    @Override
    public List<EmployeeDto> getAllByProjectId(Long projectId) {
        return teamRepository.findAllByProjectId(projectId).
                stream().
                map(Team::getEmployeeId).
                map(employeeService::getById).
                filter(Objects::nonNull).
                toList();

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

    @Override
    public Boolean existsByEmployeeIdAndProjectId(Long id, Long projectId) {
        return teamRepository.existsByEmployeeIdAndProjectId(id, projectId);
    }
}
