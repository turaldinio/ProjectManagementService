package com.digital.pm.service;

import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface TeamService {
    TeamDto addEmployee(CreateTeamDto createTeamDto);

    TeamDto delete(Long employeeId, Long projectId);

    List<EmployeeDto> getAllByProjectId(Long projectId);


    boolean existsByEmployeeIdAndProjectId(Long id, Long projectId);
}
