package com.digital.pm.dto.team;

import com.digital.pm.common.enums.Role;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.project.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private long id;
    private Map<EmployeeDto, Role> employees;

    private ProjectDto project;
}
