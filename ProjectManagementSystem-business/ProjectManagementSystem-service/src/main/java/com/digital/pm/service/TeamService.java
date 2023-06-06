package com.digital.pm.service;

import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TeamService {
    TeamDto addEmployee(CreateTeamDto createTeamDto);

    TeamDto delete(Long employeeId, Long projectId);

    List<EmployeeDto> getAllByProjectId(Long projectId);


    Boolean existsByEmployeeIdAndProjectId(Long id, Long projectId);
}
