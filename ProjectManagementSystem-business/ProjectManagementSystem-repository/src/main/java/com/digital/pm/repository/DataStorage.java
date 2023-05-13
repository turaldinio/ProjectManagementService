package com.digital.pm.repository;

import com.digital.pm.dto.EmployeeDto;

import java.util.List;

public interface DataStorage {
    EmployeeDto create();

    EmployeeDto update();

    EmployeeDto getById(int id);

    List<EmployeeDto> getAll();

    void deleteById(int id);
}
