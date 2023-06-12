package com.digital.pm.service.impl;

import com.digital.pm.model.TaskFile;
import com.digital.pm.repository.TaskFileRepository;
import com.digital.pm.service.TaskService;
import com.digital.pm.service.exceptions.BadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class TaskFileService {
    private final TaskFileRepository taskFileRepository;
    private final TaskService taskService;

    public ResponseEntity<?> saveFile(TaskFile taskFile) {
        if (Files.notExists(Path.of(taskFile.getPath()))) {
            throw new BadRequest(String.format("the file %s is not exists", taskFile.getTaskId()));
        }
        if (!taskService.existsById(taskFile.getTaskId())) {
            throw new BadRequest(String.format("the task with %d id is not found", taskFile.getTaskId()));
        }

        return ResponseEntity.ok(taskFileRepository.save(taskFile));

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

    public byte[] getFileBytes(File file){
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
