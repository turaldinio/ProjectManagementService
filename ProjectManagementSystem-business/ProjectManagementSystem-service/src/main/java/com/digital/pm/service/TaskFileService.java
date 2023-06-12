package com.digital.pm.service;

import com.digital.pm.dto.taskFiles.CreateTaskFilesDto;
import com.digital.pm.dto.taskFiles.TaskFilesDto;
import com.digital.pm.model.TaskFile;

import java.io.File;
import java.util.List;

public interface TaskFileService {
    TaskFilesDto saveFile(CreateTaskFilesDto createTaskFilesDto, Long taskId);

     File downloadFile(Long taskId);

    byte[] getFileBytes(File file);

    TaskFilesDto map(List<TaskFile> files);
}
