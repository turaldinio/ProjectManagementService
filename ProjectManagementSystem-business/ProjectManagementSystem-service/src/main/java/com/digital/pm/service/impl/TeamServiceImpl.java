package com.digital.pm.service.impl;

import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.model.team.Team;
import com.digital.pm.repository.TeamRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.TeamService;
import com.digital.pm.service.mapping.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<?> addEmployee(CreateTeamDto createTeamDto) {
        if (teamRepository.existsByEmployeeIdAndProjectId(createTeamDto.getEmployeeId(), createTeamDto.getProjectId())) {
            return ResponseEntity.
                    badRequest().
                    body(String.format("the user %d id is already participating in the project %d id with the %s role",
                            createTeamDto.getEmployeeId(),
                            createTeamDto.getProjectId(),
                            createTeamDto.getRole().name().toLowerCase()));
        }
        
        var team = teamMapper.create(createTeamDto);

        teamRepository.save(team);

        return ResponseEntity.ok(teamMapper.map(team));
    }

    @Override
    public ResponseEntity<?> delete(Long employeeId, Long projectId) {
        if (!teamRepository.existsByEmployeeIdAndProjectId(employeeId, projectId)) {
            return ResponseEntity.
                    badRequest().
                    body(String.format("the employee with %d id does not participate in the %d id project", employeeId, projectId));
        }
        var result = teamRepository.deleteByEmployeeIdAndProjectId(employeeId, projectId);

        return ResponseEntity.ok(teamMapper.map(result));
    }

    @Override
    public ResponseEntity<?> getAll(Long projectId) {
        var result = teamRepository.findAllByProjectId(projectId).
                stream().
                map(Team::getEmployeeId).
                map(x -> (EmployeeDto) employeeService.getById(x).getBody()).
                filter(Objects::nonNull).
                toList();

        return ResponseEntity.ok(result);

    }
}
