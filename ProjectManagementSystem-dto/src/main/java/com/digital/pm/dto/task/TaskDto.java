package com.digital.pm.dto.task;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.dto.employee.EmployeeDto;

import java.util.Date;

public class TaskDto {
    private int id;
    private String name;
    private String description;

    private EmployeeDto executor;

    private int laborCosts;

    private Date deadline;
    private TaskStatus status;

    private String author;

    private Date dateOfCreation;
    private Date update;
}
