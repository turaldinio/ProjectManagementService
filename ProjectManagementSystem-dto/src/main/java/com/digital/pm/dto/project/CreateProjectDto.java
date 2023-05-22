package com.digital.pm.dto.project;

import com.digital.pm.common.enums.ProjectStatus;
import lombok.Data;

@Data
public class CreateProjectDto {
    private String name;
    private String description;

}
