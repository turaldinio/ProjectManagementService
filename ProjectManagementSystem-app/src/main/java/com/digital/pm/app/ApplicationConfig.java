package com.digital.pm.app;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.web.controller.EmployeeController;

import java.util.List;

public class ApplicationConfig {
    private static final EmployeeController employeeController = new EmployeeController();

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

        System.out.println("----------CREATE-------------");

        var firstDto = employeeController.create(first);
        var secondDto = employeeController.create(second);

        System.out.println(firstDto + " is created");
        System.out.println(secondDto + " is created");


        System.out.println("----------UPDATE-------------");

        var updateResult = employeeController.update(1, third);
        printResult(updateResult);

        System.out.println("----------getById-------------");

        var getResult = employeeController.getById(2);
        printResult(getResult);

        System.out.println("----------deleteById-------------");

        var deleteResult = employeeController.deleteById(1);
        printResult(deleteResult);

        System.out.println("----------getAll-------------");
        printResult(employeeController.getAll());



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
