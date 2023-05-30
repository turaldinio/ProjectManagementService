package com.digital.pm.dto.team;

import com.digital.pm.common.enums.Role;
import com.digital.pm.dto.project.ProjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    @Schema(description = "Идентификатор команды")
    private Long id;
    @Schema(description = "id участника команды")
    private Long employeeId;
    @Schema(description = "id проекта")
    private Long projectId;
    @Schema(description = "Роль сотрудника в проекте")
    private Role role;
}
