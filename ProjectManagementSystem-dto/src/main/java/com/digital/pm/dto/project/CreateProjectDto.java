package com.digital.pm.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDto {
    @Schema(description = "Код проекта")
    private String projectCode;
    @Schema(description = "Имя проекта")
    private String name;
    @Schema(description = "Описание проекта")
    private String description;

}
