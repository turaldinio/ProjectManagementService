package com.digital.pm.web.controller;

import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CreateTeamDto createTeamDto) {
        return teamService.addEmployee(createTeamDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(Long employeeId, Long projectId) {
        return teamService.delete(employeeId, projectId);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllByProjectId(@PathVariable("id") Long projectId) {
        return teamService.getAllByProjectId(projectId);
    }
}
