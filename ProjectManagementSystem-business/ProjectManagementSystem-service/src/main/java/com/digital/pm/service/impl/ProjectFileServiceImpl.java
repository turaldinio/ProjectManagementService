package com.digital.pm.service.impl;

import com.digital.pm.dto.projectFiles.CreateProjectFileDto;
import com.digital.pm.dto.projectFiles.ProjectFilesDto;
import com.digital.pm.model.ProjectFile;
import com.digital.pm.repository.ProjectFileRepository;
import com.digital.pm.service.ProjectFileService;
import com.digital.pm.service.ProjectService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.project.ProjectFileMapping;
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
public class ProjectFileServiceImpl implements ProjectFileService {
    private final ProjectFileRepository repository;
    private final ProjectService service;
    private final ProjectFileMapping mapper;

    public ProjectFilesDto saveFile(CreateProjectFileDto createProjectFileDto, Long projectId) {
        log.info("saveFile method started");
        checkAllFiles(createProjectFileDto.getFilePath());//проверим, что переданный файл существует

        if (!service.existsById(projectId)) {//проверка, существует ли project с указанным id
            throw new BadRequest(String.format("the project with %d id is not found", projectId));
        }

        if (repository.existsByPath(createProjectFileDto.getFilePath())) {//проверка, что данный файл еще не привязан к задаче
            throw new BadRequest(String.format("file %s is already exists", createProjectFileDto.getFilePath()));
        }
        log.info("mapping CreateProjectFilesDto to ProjectFile");
        var projectFile = mapper.create(createProjectFileDto, projectId);

        log.info("saving the ProjectFile");
        var result = repository.save(projectFile);

        log.info("saveFile method is completed");
        return mapper.map(result);

    }

    private void checkAllFiles(String file) {
        if (Files.notExists(Path.of(file))) {
            throw new BadRequest(String.format("file %s is not exists", file));
        }

    }

    public File downloadFile(Long ProjectId) {
        log.info("started the downloadFile method");
        if (!service.existsById(ProjectId)) {//проверка существования Project с ProjectId
            throw new BadRequest(String.format("the Project with %d id is not found", ProjectId));
        }
        File result = null;
        try {
            result = File.createTempFile("Project_file", ".zip");//создаем временный файл
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("creating ZipOutputStream");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(result))) {

            log.info("find all Project files");
            var ProjectFiles = repository.findByProjectId(ProjectId);

            if (ObjectUtils.isEmpty(ProjectFiles)) {//нет файлов для скачивания
                log.info(String.format("the Project with id %d has no files to upload", ProjectId));//нет файлов для скачивания
                return null;
            }
            ProjectFiles.stream().map(ProjectFile::getPath).forEach(x -> {
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

    @Override
    public byte[] getFileBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
