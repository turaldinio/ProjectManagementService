package com.digital.pm.dto.task;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.dto.employee.EmployeeDto;

import java.util.Date;

public class CreateTaskDto {
    private String name;
    private String description;

    private EmployeeDto executor;

    private Date deadline;
    private String author;
    private Date update;
}
