package com.digital.pm.dto.task;

import com.digital.pm.dto.employee.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {
    private String name;
    private String description;
    private EmployeeDto executor;
    private int laborCosts;

    private Date deadline;
    private String author;
    private Date updateTime;
}
