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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskFileServiceImpl implements TaskFileService {
    private final TaskFileRepository taskFileRepository;
    private final TaskService taskService;
    private final TaskFileMapping taskFileMapping;

    public TaskFilesDto saveFile(CreateTaskFilesDto createTaskFilesDto, Long taskId) {
        log.info("saveFile method started");
        checkAllFiles(createTaskFilesDto.getFilePath());//проверим, что переданный файл существует

        if (!taskService.existsById(taskId)) {//проверка, существует ли task с указанным id
            throw new BadRequest(String.format("the task with %d id is not found", taskId));
        }

        if (taskFileRepository.existsByPath(createTaskFilesDto.getFilePath())) {
            throw new BadRequest(String.format("file %s is already exists", createTaskFilesDto.getFilePath()));
        }
        log.info("mapping CreateTaskFilesDto to TaskFile");
        var taskFile = taskFileMapping.create(createTaskFilesDto, taskId);

        log.info("saving the TaskFile");
        var result = taskFileRepository.save(taskFile);

        log.info("saveFile method is completed");
        return taskFileMapping.map(result);

    }

    private void checkAllFiles(String file) {
        if (Files.notExists(Path.of(file))) {
            throw new BadRequest(String.format("file %s is not exists", file));
        }

    }

    public File downloadFile(Long taskId) {
        log.info("started the downloadFile method");
        if (!taskService.existsById(taskId)) {//проверка существования task с taskId
            throw new BadRequest(String.format("the task with %d id is not found", taskId));
        }
        File result = null;
        try {
            result = File.createTempFile("task_file", ".zip");//создаем временный файл
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("creating ZipOutputStream");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(result))) {

            log.info("find all task files");
            var taskFiles = taskFileRepository.findByTaskId(taskId);

            if (ObjectUtils.isEmpty(taskFiles)) {//нет файлов для скачивания
                log.info(String.format("the task with id %d has no files to upload", taskId));//нет файлов для скачивания
                return null;
            }
            taskFiles.stream().map(TaskFile::getPath).forEach(x -> {
                File file = new File(x);
                log.info(String.format("writing %s file to a zip file", file.getName()));
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

        log.info("the zip file is ready to download");
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
