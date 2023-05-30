package com.digital.pm.web.controller;

import com.digital.pm.dto.task.TaskDto;
import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import com.digital.pm.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "TeamController", description = "Контроллер команды")
@ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = TeamDto.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Неправильные параметры аргумента", content = @Content(schema = @Schema(), mediaType = "application/json"))
})

@RequestMapping(value = "/private/team", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {
    private final TeamService teamService;

    @Operation(summary = "добавление сотрудника в команду",
            description = "Добавляет указанного сотрудника в указанную команду. Если сотрудник уже состоит в этй команде, выбрасывает исключение")

    @PostMapping("/add")
    public ResponseEntity<TeamDto> add(@RequestBody CreateTeamDto createTeamDto) {
        return ResponseEntity.ok(teamService.addEmployee(createTeamDto));
    }

    @Operation(summary = "удаление сотрудника из команду",
            description = "Удаляет указанного сотрудника из команды")

    @DeleteMapping("/delete")
    public ResponseEntity<TeamDto> delete(Long employeeId, Long projectId) {
        return ResponseEntity.ok(teamService.delete(employeeId, projectId));
    }

    @Operation(summary = "Список сотрудников",
            description = "Выводит всех сотрудников, работающих на указанному проекте")

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllEmployeesByProjectId(@PathVariable("id") Long projectId) {
        return ResponseEntity.ok(teamService.getAllByProjectId(projectId));
    }
}
