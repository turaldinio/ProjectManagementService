package com.digital.pm.repository.filter;

import com.digital.pm.common.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDaoFilter {

    @Schema(description = "Код проекта")

    private String projectCode;
    @Schema(description = "Имя проекта")

    private String name;
    @Schema(description = "Коллекция статусов проекта")

    private List<ProjectStatus> statusList;
}
