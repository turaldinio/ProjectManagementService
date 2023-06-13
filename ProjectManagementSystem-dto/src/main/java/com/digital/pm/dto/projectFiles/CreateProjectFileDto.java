package com.digital.pm.dto.projectFiles;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProjectFileDto {
    @Schema(description = "Путь до файла")
    private String filePath;

}
