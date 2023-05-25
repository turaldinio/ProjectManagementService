package com.digital.pm.dto.team;

import com.digital.pm.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamDto {
    private Long id;
    private Role roles;


}
