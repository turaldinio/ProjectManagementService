package com.digital.pm.dto.team;

import com.digital.pm.common.enums.Roles;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.project.ProjectDto;

import java.util.Map;

public class TeamDto {
    private long id;
    private Map<EmployeeDto, Roles> employees;

    private ProjectDto project;
}
