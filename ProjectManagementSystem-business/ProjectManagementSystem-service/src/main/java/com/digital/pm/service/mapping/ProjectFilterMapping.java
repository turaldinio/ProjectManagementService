package com.digital.pm.service.mapping;

import com.digital.pm.common.filters.project.ProjectDaoFilter;
import com.digital.pm.common.filters.project.ProjectDtoFilter;

public class ProjectFilterMapping {

    public ProjectDaoFilter create(ProjectDtoFilter projectDtoFilter) {
        return ProjectDaoFilter.builder().
                projectCode(projectDtoFilter.getProjectCode()).
                name(projectDtoFilter.getName()).
                statusList(projectDtoFilter.getStatusList()).
                build();

    }
}
