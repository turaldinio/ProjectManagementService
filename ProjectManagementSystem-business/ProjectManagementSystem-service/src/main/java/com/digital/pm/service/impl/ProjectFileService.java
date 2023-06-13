package com.digital.pm.service.impl;

import com.digital.pm.repository.ProjectFileRepository;
import com.digital.pm.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectFileService {
    private final ProjectFileRepository repository;
    private final ProjectService projectService;

}
