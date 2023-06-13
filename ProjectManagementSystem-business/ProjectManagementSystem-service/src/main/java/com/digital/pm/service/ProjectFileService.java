package com.digital.pm.service;

import com.digital.pm.dto.projectFiles.CreateProjectFileDto;
import com.digital.pm.dto.projectFiles.ProjectFilesDto;
import com.digital.pm.model.ProjectFile;

import java.io.File;
import java.util.List;


public interface ProjectFileService {
    ProjectFilesDto saveFile(CreateProjectFileDto projectFileDto, Long projectId);

    File downloadFile(Long projectId);

    byte[] getFileBytes(File file);

}
