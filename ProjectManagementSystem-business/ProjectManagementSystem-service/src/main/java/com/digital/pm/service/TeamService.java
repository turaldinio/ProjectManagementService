package com.digital.pm.service;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.team.TeamDto;

import java.util.List;

public interface TeamService {
    TeamDto addEmployee(CreateEmployeeDto createEmployeeDto);
    void delete(Long employeeId);

    List<TeamDto>getAll();
}
