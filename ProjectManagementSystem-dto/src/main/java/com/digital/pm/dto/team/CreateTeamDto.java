package com.digital.pm.dto.team;

import com.digital.pm.common.enums.Roles;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.project.ProjectDto;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

public class CreateTeamDto {
    // TODO: 22.05.2023 нужен Employee (его нет в dependency)
    private Long id;
    private Roles roles;


}
