package com.digital.pm.app;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ProjectManagementSystemApplication implements CommandLineRunner {
    @Autowired
    private DataBaseService dataBaseService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
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
                lastName("Лахимов").
                firstName("Илья").
                patronymic("Юрьевич").
                post("junior developer").
                account("kiOkr3").
                email("laxiSu32@mail.ru").
                build();


        System.out.println("-------------CREATE------------");
        printResult(dataBaseService.create(first));
        printResult(dataBaseService.create(second));
//
//        System.out.println("-------------GETALL------------");
//
//        printResult(employeeController.getAll());
//
//
//        System.out.println("-------------GETBYID------------");
//
//        printResult(employeeController.getById(2L));
//
//        System.out.println("-------------DELETE------------");
//        printResult(employeeController.deleteById(1));
//
//        System.out.println("-------------UPDATE------------");
//        printResult(employeeController.update(1L, third));
//
//        System.out.println("-------------FILTER------------");
//        var filterEmployee = FilterEmployee.builder().firsName("Сергей").build();
//
//        printResult(employeeController.searchByFilter(filterEmployee));

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