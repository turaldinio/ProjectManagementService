package com.digital.pm.app;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.employee.FilterEmployee;
import com.digital.pm.service.Impl.EmployeeServiceImpl;
import com.digital.pm.service.Impl.JdbcServiceImpl;
import com.digital.pm.web.controller.EmployeeController;
import com.digital.pm.web.controller.JdbcController;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ApplicationConfig {
    private static final JdbcController jdbcController = new JdbcController(new JdbcServiceImpl());
    private static final EmployeeController employeeController =
            new EmployeeController(
                    new EmployeeServiceImpl(jdbcController.getConnection("jdbc:mysql://localhost:3306/digital",
                            "root", "password")));


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
                lastName("Лахимов").
                firstName("Илья").
                patronymic("Юрьевич").
                post("junior developer").
                account("kiOkr3").
                email("laxiSu32@mail.ru").
                build();
        System.out.println("-------------CREATE------------");
        printResult(employeeController.create(first));
        printResult(employeeController.create(second));

        System.out.println("-------------GETALL------------");

        printResult(employeeController.getAll());


        System.out.println("-------------GETBYID------------");

        printResult(employeeController.getById(2L));

//        System.out.println("-------------DELETE------------");
//        printResult(employeeController.deleteById(1));

        System.out.println("-------------UPDATE------------");
        printResult(employeeController.update(1L, third));

        System.out.println("-------------FILTER------------");
        var filterEmployee = FilterEmployee.builder().firsName("Сергей").build();

        printResult(employeeController.searchByFilter(filterEmployee));


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
