package com.digital.pm.dto.project;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDto {
    @NotNull
    private String projectCode;
    @NotNull
    private String name;
    private String description;

}
