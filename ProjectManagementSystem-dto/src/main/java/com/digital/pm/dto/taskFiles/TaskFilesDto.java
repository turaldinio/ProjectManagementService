package com.digital.pm.dto.taskFiles;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskFilesDto {
    @Schema(description = "путь до файла")
    private String filePath;

}
