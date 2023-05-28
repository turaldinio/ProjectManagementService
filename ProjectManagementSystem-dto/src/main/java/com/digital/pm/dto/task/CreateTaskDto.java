package com.digital.pm.dto.task;

import com.digital.pm.dto.employee.EmployeeDto;
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
    private String name;
    private String description;
    private Long executorId;
    private Long projectId;
    private Long laborCost; //трудозатраты в часах
    private Date deadline;
}
