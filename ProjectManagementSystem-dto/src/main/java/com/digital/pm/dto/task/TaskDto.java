package com.digital.pm.dto.task;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.dto.employee.EmployeeDto;

import java.util.Date;

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

    private Date deadline;
    @Schema(description = "Дата создания")

    private Date creationDate;
    @Schema(description = "Дата обновления")
    private Date updateTime;
    @Schema(description = "Статус задачи",defaultValue = "NEW")

    private TaskStatus status;

}
