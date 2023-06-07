package com.digital.pm.app.integration;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.credential.CreateCredentialDto;
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

    /**
     * проверка метода создания пользователя
     * Создаем сущность, и записываем ее в бд
     */
    @Test
    public void create() {
        var createEmployeeDto = CreateEmployeeDto.builder().
                firstName("Евгений").
                lastName("Романов").
                build();
        var result = employeeService.create(createEmployeeDto);

        Assertions.assertNotNull(result);
    }

    /**
     * Проверка выбрасывания исключения при невалидных данных
     * Создаем пользователя и записываем его в бд
     * Пробуем повторно записать его в бд (ожидаем exception)
     */
    @Test
    public void createWithException() {
        var employee1 = CreateEmployeeDto.
                builder().
                firstName("Евгений").
                lastName("Дамиров").
                createCredentialDto(CreateCredentialDto.builder().
                        login("ioli").
                        password("user").build()).
                build();

        employeeService.create(employee1);

        Assertions.assertThrows(BadRequest.class, () -> employeeService.create(employee1));


    }

    /**
     * Проверка обновления пользователя. Создаем сотрудника с именем Евгений,
     * после чего обновляем его имя до Александр
     */
    @Test
    public void updateEmployee() {
        var createEmployeeDto = CreateEmployeeDto.
                builder().
                firstName("Илья").
                lastName("Ляхов").
                build();

        var create = employeeService.create(createEmployeeDto);//Илья

        var updateEmployer = createDefaultEmployer();
        updateEmployer.setFirstName("Александр");//Александр

        var result = employeeService.update(create.getId(), updateEmployer);

        Assertions.assertNotNull(result);
    }

    /**
     * Проверка метода получения всех сотрудников
     * Направляем запрос на получение всх записей в бд(там точно есть записи liqubase)
     */

    @Test
    public void findAll() {
        Assertions.assertNotNull(employeeService.findAll());
    }

    /**
     * Проверка возмонжости поиска сущности по фильтру
     * Создаем сущность, записываем ее в бд
     * Создаем фильтр с аккаунтом =employee.account
     * Осуществляем поиск
     * Проверяем что сотрудник найден
     */
    @Test
    public void findAllWithFilter() {
        var employee = CreateEmployeeDto.builder().
                firstName("Денис").
                lastName("Романов").
                patronymic("Елесенкович").
                post("junior").
                email("poli98@mail.ru").
                createCredentialDto(CreateCredentialDto.builder().
                        login("iolit").
                        password("user").build()).
                build();

        var create = employeeService.create(employee);//записываем в бд

        var filter = EmployeeFilter.
                builder().
                login(create.getCredentialDto().getLogin()).
                build();

        var result = employeeService.findAll(filter);//ищем по фильтру account

        Assertions.assertEquals(employee.getFirstName(), result.stream().findFirst().orElseThrow().getFirstName());
    }

    /**
     * Проверка удаления пользователя по id
     * Получаем всех сотрудников
     * Получаем id последнего сотрудника
     * Удаляем пользователя по этому id
     * Проверяем, что при повторном удалении пользователя выкинется исключение
     */
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
