package com.digital.pm.app;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.web.controller.EmployeeController;

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

//        System.out.println("----------CREATE-------------");
//
//        var firstDto = employeeController.create(first);
//        var secondDto = employeeController.create(second);
//
//        System.out.println(firstDto + " is created");
//        System.out.println(secondDto + " is created");
//


//        System.out.println("----------UPDATE-------------");
//
//        var thirdDto = employeeController.update(1, third);
//        System.out.println(thirdDto + " is updated");
//


        //        System.out.println("----------GetById-------------");

//        System.out.println("----------getById-------------");
//        System.out.println(employeeController.getById(98));

        employeeController.getAll().forEach(System.out::println);
    }
}
