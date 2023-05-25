package com.digital.pm.common.filters;

import com.digital.pm.common.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFilter {
    private Long id;
    private String projectCode;
    private String name;
    private String description;
    private ProjectStatus status;
}
