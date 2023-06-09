package com.digital.pm.web.controller;

import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import com.digital.pm.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@RequestMapping("/private/team")
public class TeamController {
    private final TeamService teamService;

    @Operation(summary = "добавление сотрудника в команду",
            description = "Добавляет указанного сотрудника в указанную команду. Если сотрудник уже состоит в этй команде, выбрасывает исключение")

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDto> add(@RequestBody CreateTeamDto createTeamDto) {
        return ResponseEntity.ok(teamService.addEmployee(createTeamDto));
    }

    @Operation(summary = "удаление сотрудника из команду",
            description = "Удаляет указанного сотрудника из команды")

    @DeleteMapping(value = "/delete/{employeeId}/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDto> delete(@Parameter(description = "id сотрудника, которого хотим удалить из команды")
                                          @PathVariable Long employeeId,
                                          @Parameter(description = "id команды, из которой удаляем сотрудника")
                                          @PathVariable Long projectId) {
        return ResponseEntity.ok(teamService.delete(employeeId, projectId));
    }

    @Operation(summary = "Список сотрудников",
            description = "Выводит всех сотрудников, работающих на указанному проекте")

    @GetMapping(value = "/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEmployeesByProjectId(@Parameter(description = "id проекта, для которого необходимо получить всех участников")
                                                        @PathVariable("id") Long projectId) {
        return ResponseEntity.ok(teamService.getAllByProjectId(projectId));
    }
}
