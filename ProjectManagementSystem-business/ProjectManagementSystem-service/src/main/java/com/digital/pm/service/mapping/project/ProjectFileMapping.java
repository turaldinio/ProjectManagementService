package com.digital.pm.service.mapping.project;

import com.digital.pm.dto.projectFiles.CreateProjectFileDto;
import com.digital.pm.dto.projectFiles.ProjectFilesDto;
import com.digital.pm.model.ProjectFile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectFileMapping {

    public ProjectFile create(CreateProjectFileDto createProjectFileDto, Long projectId) {
        return ProjectFile.
                builder().
                projectId(projectId).
                path(createProjectFileDto.getFilePath()).
                build();
    }

    public ProjectFilesDto map(ProjectFile projectFile) {
        return ProjectFilesDto.
                builder().
                filePath(projectFile.getPath()).
                build();
    }

    public List<ProjectFilesDto> map(List<ProjectFile> files) {
        return files == null ? Collections.emptyList() :
                files.
                        stream().
                        map(this::map).
                        collect(Collectors.toList());
    }
}
