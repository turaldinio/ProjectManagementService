package com.digital.pm.service;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamService {
    ResponseEntity<?> addEmployee(CreateTeamDto createTeamDto);
    ResponseEntity<?> delete(Long employeeId,Long projectId);

    ResponseEntity<?>getAll(Long projectId);
}
