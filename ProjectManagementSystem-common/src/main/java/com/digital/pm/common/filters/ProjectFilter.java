package com.digital.pm.common.filters;

import com.digital.pm.common.enums.ProjectStatus;
import lombok.Data;

@Data
public class ProjectFilter {
    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
}
