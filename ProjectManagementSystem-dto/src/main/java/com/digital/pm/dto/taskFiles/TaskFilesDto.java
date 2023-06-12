package com.digital.pm.dto.taskFiles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskFilesDto {
    private Long taskId;
    private List<String> filePaths;

}
