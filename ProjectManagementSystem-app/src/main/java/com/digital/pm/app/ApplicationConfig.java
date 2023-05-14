package com.digital.pm.app;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.web.controller.EmployeeController;

public class ApplicationConfig {
    private static final EmployeeController employeeController = new EmployeeController();

    public static void main(String[] args) {
        CreateEmployeeDto createEmployeeDto = CreateEmployeeDto.
                builder().
                lastName("Романов").
                firstName("Сергей").
                patronymic("Иванович").
                post("junior developer").
                account("sergRom98").
                email("romaniv@mail.ru").
                build();

        CreateEmployeeDto createEmployeeDto1 = CreateEmployeeDto.
                builder().
                lastName("Иванов").
                firstName("Евгений").
                patronymic("Романович").
                post("junior developer").
                account("ivanka").
                email("omtom@bk.ru").
                build();

        var employeeDto = employeeController.create(createEmployeeDto);
        var employeeDto1 = employeeController.create(createEmployeeDto1);

        System.out.println(employeeDto+" is created");
        System.out.println(employeeDto1+" is created");


    }
}
