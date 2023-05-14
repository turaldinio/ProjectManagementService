package com.digital.pm.app;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.service.Impl.EmployeeServiceImpl;
import com.digital.pm.web.controller.EmployeeController;

public class ApplicationConfig {
    private static final EmployeeController employeeController = new EmployeeController();

    public static void main(String[] args) {
        CreateEmployeeDto createEmployeeDto = CreateEmployeeDto.
                builder().
                lastName("Романов").
                firstName("Сергей").
                patronymic("Иванович").
                post("Team-lid").
                account("sergRom98").
                email("romaniv@mail.ru").
                build();

        var employeeDto=employeeController.create(createEmployeeDto);


    }
}
