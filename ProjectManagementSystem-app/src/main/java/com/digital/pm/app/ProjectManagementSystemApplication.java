package com.digital.pm.app;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.DataBaseService;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.Impl.EmployeeServiceImpl;
import com.digital.pm.web.controller.EmployeeController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor


public class ProjectManagementSystemApplication implements CommandLineRunner {
    private final DataBaseService dataBaseService;
    private final DataBaseService dataBaseFileService;
    private final EmployeeController employeeController;


    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);

    }

    //    @Override
    public void run(String... args) throws Exception {
        System.out.println(employeeController.
                findOne(EmployeeFilter.
                builder().
                status(EmployeeStatus.ACTIVE).
                build()));

//        var result=employeeService.findOne(EmployeeFilter.
//                builder().
//                status(EmployeeStatus.ACTIVE).
//                build());
//        printResult(result);
//    CreateEmployeeDto first = CreateEmployeeDto.
//            builder().
//            id(0).
//            lastName("Романов").
//            firstName("Сергей").
//            patronymic("Иванович").
//            post("junior developer").
//            account("sergRom98").
//            email("romaniv@mail.ru").
//            build();
//
//        CreateEmployeeDto second = CreateEmployeeDto.
//                builder().
//                lastName("Иванов").
//                firstName("Евгений").
//                patronymic("Романович").
//                post("junior developer").
//                account("ivanka").
//                email("omtom@bk.ru").
//                build();
//
//        CreateEmployeeDto third = CreateEmployeeDto.
//                builder().
//                lastName("Лахимов").
//                firstName("Илья").
//                patronymic("Юрьевич").
//                post("junior developer").
//                account("kiOkr3").
//                email("laxiSu32@mail.ru").
//                build();
//
//
//        System.out.println("-------------CREATE------------");
//        printResult(dataBaseService.create(first));
//        printResult(dataBaseService.create(second));
//

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
