package com.digital.pm.service.impl;

import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import com.digital.pm.model.team.Team;
import com.digital.pm.repository.TeamRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.TeamService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final EmployeeService employeeService;

    @Override
    public TeamDto addEmployee(CreateTeamDto createTeamDto) {
        if (teamRepository.existsByEmployeeIdAndProjectId(createTeamDto.getEmployeeId(), createTeamDto.getProjectId())) {
            throw invalidEmployeeOrProjectId(createTeamDto);
        }
        var team = teamMapper.create(createTeamDto);

        teamRepository.save(team);

        return teamMapper.map(team);
    }

    @Override
    public TeamDto delete(Long employeeId, Long projectId) {
        if (!teamRepository.existsByEmployeeIdAndProjectId(employeeId, projectId)) {
            throw invalidEmployeeOrProjectId(employeeId, projectId);
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


    public BadRequest invalidEmployeeOrProjectId(CreateTeamDto createTeamDto) {
        return new BadRequest(String.format("the user %d id is already participating in the project %d id with the %s role",
                createTeamDto.getEmployeeId(),
                createTeamDto.getProjectId(),
                createTeamDto.getRole().name().toLowerCase()));
    }

    public BadRequest invalidEmployeeOrProjectId(Long employeeId, Long projectId) {
        return new BadRequest(String.format("the employee with %d id does not participate in the %d id project", employeeId, projectId));
    }

    @Override
    public boolean existsByEmployeeIdAndProjectId(Long id, Long projectId) {
        return teamRepository.existsByEmployeeIdAndProjectId(id,projectId);
    }
}
