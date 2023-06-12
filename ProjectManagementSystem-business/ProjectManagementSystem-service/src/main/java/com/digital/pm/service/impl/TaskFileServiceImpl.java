package com.digital.pm.service.impl;

import com.digital.pm.dto.taskFiles.CreateTaskFilesDto;
import com.digital.pm.dto.taskFiles.TaskFilesDto;
import com.digital.pm.model.TaskFile;
import com.digital.pm.repository.TaskFileRepository;
import com.digital.pm.service.TaskFileService;
import com.digital.pm.service.TaskService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.TaskFileMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class TaskFileServiceImpl implements TaskFileService {
    private final TaskFileRepository taskFileRepository;
    private final TaskService taskService;
    private final TaskFileMapping taskFileMapping;

    public TaskFilesDto saveFile(CreateTaskFilesDto createTaskFilesDto, Long taskId) {
        checkAllFiles(createTaskFilesDto.getFilePath());//проверим, что переданный файл существует

        if (!taskService.existsById(taskId)) {//проверка, существует ли task с указанным id
            throw new BadRequest(String.format("the task with %d id is not found", taskId));
        }

        if (taskFileRepository.existsByPath(createTaskFilesDto.getFilePath())) {
            throw new BadRequest(String.format("file %s is already exists", createTaskFilesDto.getFilePath()));
        }

        var taskFile = taskFileMapping.create(createTaskFilesDto, taskId);

        var result = taskFileRepository.save(taskFile);

        return taskFileMapping.map(result);

    }

    private void checkAllFiles(String file) {
        if (Files.notExists(Path.of(file))) {
            throw new BadRequest(String.format("file %s is not exists", file));
        }

    }

    public File downloadFile(Long taskId) {
        if (!taskService.existsById(taskId)) {
            throw new BadRequest(String.format("the task with %d id is not found", taskId));
        }
        File result = null;
        try {
            result = File.createTempFile("task_file", ".zip");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(result))) {

            var taskFiles = taskFileRepository.findByTaskId(taskId);

            taskFiles.stream().map(TaskFile::getPath).forEach(x -> {
                File file = new File(x);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                try {
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.write(Files.readAllBytes(file.toPath()));

                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;

    }

    public byte[] getFileBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TaskFilesDto map(List<TaskFile> files) {
        return null;
    }
}
