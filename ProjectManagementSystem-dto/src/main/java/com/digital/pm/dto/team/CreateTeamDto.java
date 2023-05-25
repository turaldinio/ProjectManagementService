package com.digital.pm.dto.team;

import com.digital.pm.common.enums.Role;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.project.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamDto {
    private Long employeeId;
    private Long projectId;

    private Role role;

}
