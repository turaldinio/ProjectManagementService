package com.digital.pm.web.controller;

import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import com.digital.pm.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/private/team",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/add")
    public ResponseEntity<TeamDto> add(@RequestBody CreateTeamDto createTeamDto) {
        return ResponseEntity.ok(teamService.addEmployee(createTeamDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TeamDto> delete(Long employeeId, Long projectId) {
        return ResponseEntity.ok(teamService.delete(employeeId, projectId));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllEmployeeByProjectId(@PathVariable("id") Long projectId) {
        return ResponseEntity.ok(teamService.getAllByProjectId(projectId));
    }
}
