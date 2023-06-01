package com.digital.pm.app.integration;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.exceptions.BadRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest()
public class EmployeeRepositoryTest extends PsqlContainer {
    @Autowired
    EmployeeService employeeService;

    @Test
    public void create() {
        var createEmployeeDto = CreateEmployeeDto.builder().
                firstName("Евгений").
                lastName("Романов").
                account("evKe").
                build();
        employeeService.create(createEmployeeDto);

        Assertions.assertNotNull(employeeService.findAll(EmployeeFilter.builder()
                .firstName("Евгений").lastName("Романов").build()));
    }

    @Test
    public void createWithException() {
        var employee1 = createDefaultEmployer();
        employee1.setAccount("employee");

        employeeService.create(employee1);

        var employee2 = createDefaultEmployer();
        employee2.setAccount("employee");

        Assertions.assertThrows(BadRequest.class, () -> employeeService.create(employee2));


    }

    @Test
    public void updateEmployee() {
        var createEmployeeDto = createDefaultEmployer();

        var result = employeeService.create(createEmployeeDto);

        var updateEmployer = createDefaultEmployer();
        updateEmployer.setAccount("venom98");

        employeeService.update(result.getId(), updateEmployer);

        Assertions.assertNotNull(employeeService.findAll(
                EmployeeFilter.builder().
                        account("venom98").build()
        ));
    }

    @Test
    public void findAll() {
        Assertions.assertNotNull(employeeService.findAll());
    }

    //поиск по фильтру
    @Test
    public void findAllWithFilter() {
        var employee = CreateEmployeeDto.builder().
                firstName("Денис").
                lastName("Романов").
                patronymic("Елесенкович").
                post("junior").
                email("poli98@mail.ru").
                account("denisOc").
                password("user").
                build();

        employeeService.create(employee);//записываем в бд

        var filter = EmployeeFilter.
                builder().
                account("denisOc").
                build();

        var result = employeeService.findAll(filter);//ищем по фильтру account

        Assertions.assertEquals(employee.getFirstName(), result.stream().findFirst().orElseThrow().getFirstName());
    }


    @Test
    public void deleteById() {
        var allUsers = employeeService.findAll();
        var result = employeeService.deleteById(allUsers.get(allUsers.size() - 1).getId());

        //удаляем пользователя
        Assertions.assertEquals(result.getStatus(), EmployeeStatus.REMOTE);


        //не удастся повторно удалить пользователя, который уже находится в статусе remote
        Assertions.assertThrows(BadRequest.class, () -> employeeService.deleteById(allUsers.get(allUsers.size() - 1).getId()));


    }


    public CreateEmployeeDto createDefaultEmployer() {
        return CreateEmployeeDto.builder().
                firstName("Евгений").
                lastName("Романов").
                build();
    }


}
