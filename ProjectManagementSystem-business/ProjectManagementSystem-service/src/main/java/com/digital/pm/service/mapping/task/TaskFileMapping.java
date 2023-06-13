package com.digital.pm.service.mapping.task;

import com.digital.pm.dto.taskFiles.CreateTaskFilesDto;
import com.digital.pm.dto.taskFiles.TaskFilesDto;
import com.digital.pm.model.TaskFile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskFileMapping {
    public TaskFile create(CreateTaskFilesDto createTaskFilesDto, Long taskId) {
        return TaskFile.
                builder().
                taskId(taskId).
                path(createTaskFilesDto.getFilePath()).
                build();
    }

    public TaskFilesDto map(TaskFile taskFile) {
        return TaskFilesDto.
                builder().
                filePath(taskFile.getPath()).
                build();
    }

    public List<TaskFilesDto> map(List<TaskFile> files) {
        return files == null ? Collections.emptyList() :
                files.
                        stream().
                        map(this::map).
                        collect(Collectors.toList());
    }
}
