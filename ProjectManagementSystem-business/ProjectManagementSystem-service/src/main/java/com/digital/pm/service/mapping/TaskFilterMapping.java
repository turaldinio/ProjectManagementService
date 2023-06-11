package com.digital.pm.service.mapping;

import com.digital.pm.common.filters.task.TaskDaoFilter;
import com.digital.pm.common.filters.task.TaskDtoFilter;

public class TaskFilterMapping {
    public TaskDaoFilter create(TaskDtoFilter taskDtoFilter){
        return TaskDaoFilter.builder().
                name(taskDtoFilter.getName()).
                statusList(taskDtoFilter.getStatusList()).
                authorId(taskDtoFilter.getAuthorId()).
                executorId(taskDtoFilter.getExecutorId()).
                createdAfter(taskDtoFilter.getCreatedAfter()).
                createdBefore(taskDtoFilter.getCreatedBefore()).
                deadlineBefore(taskDtoFilter.getDeadlineBefore()).
                deadlineAfter(taskDtoFilter.getDeadlineAfter()).
                build();
    }
}
