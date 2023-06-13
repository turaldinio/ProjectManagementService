package com.digital.pm.dto.project;

import com.digital.pm.common.enums.ProjectStatus;
import com.digital.pm.dto.projectFiles.ProjectFilesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectDto {
    @Schema(description = "Идентификатор проекта",defaultValue = "autoincrement")
    private Long id;
    @Schema(description = "Имя проекта")

    private String name;
    @Schema(description = "Описание проекта")

    private String description;
    @Schema(description = "Код проекта")

    private String projectCode;
    @Schema(description = "Статус проекта",defaultValue = "DRAFT")

    private ProjectStatus status;
    @Schema(description = "Файлы проекта")

    private List<ProjectFilesDto> projectFilesDto;

}
