package com.digital.pm.dto.task;

import com.digital.pm.dto.employee.EmployeeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class    CreateTaskDto {
    @Schema(description = "Имя задачи")

    private String name;
    @Schema(description = "Описание задачи")

    private String description;
    @Schema(description = "Исполнитель задачи")

    private Long executorId;
    @Schema(description = "Проект за которым закреплена задача")

    private Long projectId;
    @Schema(description = "Трудозатраты в часах")

    private Long laborCost; //трудозатраты в часах
    @Schema(description = "Срок сдачи")

    private Date deadline;
}
