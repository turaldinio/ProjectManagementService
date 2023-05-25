package com.digital.pm.service;

import com.digital.pm.dto.team.CreateTeamDto;
import org.springframework.http.ResponseEntity;


public interface TeamService {
    ResponseEntity<?> addEmployee(CreateTeamDto createTeamDto);

    ResponseEntity<?> delete(Long employeeId, Long projectId);

    ResponseEntity<?> getAllByProjectId(Long projectId);

    boolean existsByEmployeeIdAndProjectId(Long employeeId, Long projectId);

}
