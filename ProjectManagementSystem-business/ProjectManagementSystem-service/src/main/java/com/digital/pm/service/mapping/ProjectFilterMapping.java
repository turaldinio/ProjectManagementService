package com.digital.pm.service.mapping;

import com.digital.pm.repository.filter.ProjectDaoFilter;
import com.digital.pm.common.filters.project.ProjectDtoFilter;
import org.springframework.stereotype.Component;

@Component
public class ProjectFilterMapping {

    public ProjectDaoFilter create(ProjectDtoFilter projectDtoFilter) {
        return ProjectDaoFilter.builder().
                projectCode(projectDtoFilter.getProjectCode()).
                name(projectDtoFilter.getName()).
                statusList(projectDtoFilter.getStatusList()).
                build();

    }
}
