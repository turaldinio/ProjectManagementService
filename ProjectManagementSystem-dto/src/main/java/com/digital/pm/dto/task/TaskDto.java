package com.digital.pm.dto.task;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.dto.employee.EmployeeDto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private long id;
    private String name;
    private String description;

    private EmployeeDto executor;

    private int laborCosts;

    private Date deadline;
    private TaskStatus status;

    private String author;

    private Date dateOfCreation;
    private Date updateTime;
}
