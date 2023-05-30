package com.digital.pm.common.filters;

import com.digital.pm.common.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskFilter {
    private String name;
    private List<TaskStatus> statusList;
    private Long authorId;
    private Long executorId;
    private Date createdAfter;
    private Date createdBefore;
    private Date deadlineBefore;
    private Date deadlineAfter;
}
