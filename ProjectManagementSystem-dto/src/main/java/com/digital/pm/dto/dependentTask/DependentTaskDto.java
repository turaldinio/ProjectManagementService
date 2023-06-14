package com.digital.pm.dto.dependentTask;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DependentTaskDto {
    @Schema(description = "id зависимой задачи")
    private Long dependentTaskId;

}
