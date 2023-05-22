package com.digital.pm.dto.team;

import com.digital.pm.common.enums.Position;
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
    private Map<EmployeeDto, Position> employees;

    private ProjectDto project;
}
