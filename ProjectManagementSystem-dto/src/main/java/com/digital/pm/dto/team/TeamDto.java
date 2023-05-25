package com.digital.pm.dto.team;

import com.digital.pm.common.enums.Role;
import com.digital.pm.dto.project.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private Long id;
    private Long employeeId;
    private Long projectId;

    private Role role;
}
