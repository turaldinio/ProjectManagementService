package com.digital.pm.common.filters;

import com.digital.pm.common.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TaskFilter {
    private Long id;
    private String name;
    private String description;
    private int laborCosts;

    private Date deadline;
    private TaskStatus status;

    private String author;
    private Date dateOfCreation;
    private Date updateTime;
}
