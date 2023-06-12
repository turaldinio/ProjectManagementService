package com.digital.pm.dto.task;

import com.digital.pm.common.enums.TaskStatus;

import java.util.Date;

import com.digital.pm.dto.taskFiles.TaskFilesDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @Schema(description = "Идентификатор задачи")

    private Long id;
    @Schema(description = "Имя задачи")

    private String name;
    @Schema(description = "Описание задачи")

    private String description;
    @Schema(description = "Исполнитель задачи")

    private Long executorId;
    @Schema(description = "Трудозатраты в часах")

    private Long laborCosts;
    @Schema(description = "Автор задачи")

    private Long authorId;
    @Schema(description = "Проект за которым закреплена задача")

    private Long projectId;
    @Schema(description = "Срок сдачи")
    @JsonFormat(pattern = "yyyy-MM-dd Z", shape = JsonFormat.Shape.STRING)


    private Date deadline;
    @Schema(description = "Дата создания")
    @JsonFormat(pattern = "yyyy-MM-dd Z", shape = JsonFormat.Shape.STRING)


    private Date creationDate;
    @Schema(description = "Дата обновления")
    @JsonFormat(pattern = "yyyy-MM-dd Z", shape = JsonFormat.Shape.STRING)

    private Date updateTime;
    @Schema(description = "Статус задачи", defaultValue = "NEW")

    private TaskStatus status;

    private TaskFilesDto taskFilesDto;
}
