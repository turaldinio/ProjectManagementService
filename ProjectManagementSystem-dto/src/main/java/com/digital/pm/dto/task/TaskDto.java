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
    private Long id;
    private String name;
    private String description;
    private Long executorId;
    private Long laborCosts;
    private Long authorId;
    private Long projectId;
    private Date deadline;
    private Date creationDate;
    private Date updateTime;
    private TaskStatus status;

}
