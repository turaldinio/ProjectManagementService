package com.digital.pm.app;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.Impl.EmployeeServiceImpl;
import com.digital.pm.web.controller.EmployeeController;
import com.digital.pm.web.controller.JdbcController;

import java.sql.SQLException;
import java.util.List;

public class ApplicationConfig {
    private static final JdbcController jdbcController = new JdbcController(
            "jdbc:mysql://localhost:3306/digital", "root", "root"
    );
    private static final EmployeeController employeeController = new EmployeeController(new EmployeeServiceImpl(jdbcController.getConnection()));


    public static void main(String[] args) {
        CreateEmployeeDto first = CreateEmployeeDto.
                builder().
                lastName("Романов").
                firstName("Сергей").
                patronymic("Иванович").
                post("junior developer").
                account("sergRom98").
                email("romaniv@mail.ru").
                build();

        CreateEmployeeDto second = CreateEmployeeDto.
                builder().
                lastName("Иванов").
                firstName("Евгений").
                patronymic("Романович").
                post("junior developer").
                account("ivanka").
                email("omtom@bk.ru").
                build();

        CreateEmployeeDto third = CreateEmployeeDto.
                builder().
                lastName("Маминов").
                firstName("Илья").
                patronymic("Дмитриевич").
                post("teamlead").
                account("ilUshEk93").
                email("ilushka93@mail.ru").
                build();


        printResult(employeeController.getById(1));


    }

    public static void printResult(EmployeeDto employeeDto) {
        if (employeeDto != null) {
            System.out.println(employeeDto);
        }
    }

    public static void printResult(List<EmployeeDto> employeeDtoList) {
        employeeDtoList.forEach(System.out::println);
    }
}
