package com.digital.pm.web.controller;

import com.digital.pm.dto.employee.CreateEmployeeDto;

import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.service.EmployeeService;
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

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "EmployeeController", description = "Контроллер сотрудников")
@ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EmployeeDto.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Неправильные параметры аргумента", content = @Content(schema = @Schema(), mediaType = "application/json"))
})
@RequestMapping("/private/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Operation(summary = "создание сотрудника",
            description = "Создает сотрудника по указанным данным. Id инкрементируется автоматически")

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> create(@RequestBody CreateEmployeeDto createEmployeeDto) {
        return ResponseEntity.ok(employeeService.create(createEmployeeDto));
    }

    @Operation(summary = "удаление сотрудника",
            description = "Удаляет сотрудника по указанному id")

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @RequestBody CreateEmployeeDto createEmployeeDto) {
        return ResponseEntity.ok(employeeService.update(id, createEmployeeDto));
    }

    @Operation(summary = "Получить сотрудника по id",
            description = "получает сотрудника по указанному id")

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @Operation(summary = "Получить всех сотрудников",
            description = "получить всех сотрудников")

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Operation(summary = "Удалить сотрудника ",
            description = "Удаление сотрудника по указанному id")

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deleteById(id));
    }

    @Operation(summary = "Поиск сотрудника по фильтрам",
            description = "Поиск осуществляется на основе переданного объекта EmployeeFilter, поля которого необязательны к заполнению")

    @PostMapping(value = "/find", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDto>> findAll(@RequestBody(required = false) EmployeeFilter employeeFilter) {
        return ResponseEntity.ok(employeeService.findAll(employeeFilter));
    }
}
