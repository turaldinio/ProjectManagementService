package com.digital.pm.repository.filter;

import com.digital.pm.common.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class TaskDaoFilter {
    @Schema(description = "Имя задачи")
    private String name;
    private List<TaskStatus> statusList;
    @Schema(description = "Id автора задачи")
    private Long authorId;
    @Schema(description = "Id исполнителя задачи")
    private Long executorId;
    @Schema(description = "Созданные после", example = "yyyy-mm-dd")
    private Date createdAfter;
    @Schema(description = "Созданные до", example = "yyyy-mm-dd")
    private Date createdBefore;
    @Schema(description = "Срок сдачи до", example = "yyyy-mm-dd")
    private Date deadlineBefore;
    @Schema(description = "Срок сдачи после", example = "yyyy-mm-dd")
    private Date deadlineAfter;
}
