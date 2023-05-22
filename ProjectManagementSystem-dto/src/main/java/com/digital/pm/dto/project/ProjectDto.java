package com.digital.pm.dto.project;

import com.digital.pm.common.enums.ProjectStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
}
