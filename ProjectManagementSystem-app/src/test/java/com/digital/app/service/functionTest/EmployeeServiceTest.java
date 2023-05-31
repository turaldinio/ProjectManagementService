package com.digital.app.service.functionTest;

import com.digital.pm.app.ProjectManagementSystemApplication;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.employee.Employee;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.impl.EmployeeServiceImpl;
import com.digital.pm.service.mapping.EmployeeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProjectManagementSystemApplication.class)

public class EmployeeServiceTest {

    @Spy
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    /**
     * Проверка выбрасывания ошибок при отсутствии required полей (firstname & lastname)
     * expected:BadRequest
     */
    @Test
    @DisplayName("absence of first and last name")
    public void negativeEmployeeCreateFirst() {
        var createEmployeeDto = CreateEmployeeDto.
                builder().
                build();

        Assertions.assertThrows(BadRequest.class, () -> employeeService.create(createEmployeeDto));
    }


    /**
     * Проверка выбрасывания ошибок при наличии в бд дубликатов аккаунтов
     * expected:BadRequest
     */
    @Test
    @DisplayName("duplicate employeeAccount")
    public void negativeEmployeeCreateSecond() {
//имитируем наличие в бд аккаунта
        when(employeeRepository.existsByAccount(anyString())).thenReturn(true);

        var employee = createMinRequiredCreateEmployeeDto();

        Assertions.assertThrows(BadRequest.class, () -> employeeService.create(employee));

    }

    /**
     * Проверка выбрасывания ошибок при отсутствии id в бд
     * expected:BadRequest
     */
    @Test
    @DisplayName("non-existing id")
    public void negativeEmployeeUpdateFirst() {
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(BadRequest.class, () -> employeeService.update(1L, createMinRequiredCreateEmployeeDto()));

    }

    /**
     * Проверка отработки метода update
     */
    @Test
    @DisplayName("update Employee")
    public void updateEmployee() {
        var createEmployeeDto = createMinRequiredCreateEmployeeDto();
        var oldEmployee = createMinRequiredEmployee(createEmployeeDto);

        var newEmployee = createMinRequiredEmployee(createEmployeeDto);
        newEmployee.setFirstName("Денис");

        var newEmployeeDto = createMinRequiredEmployeeDto(newEmployee);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(oldEmployee));
        when(employeeRepository.existsByAccount(anyString())).thenReturn(false);
        when(employeeRepository.save(any())).thenReturn(newEmployee);

        when(employeeMapper.update(any(), any())).thenReturn(newEmployee);
        when(employeeMapper.map(newEmployee)).thenReturn(newEmployeeDto);

        var result = employeeService.update(1L, createMinRequiredCreateEmployeeDto());

        Assertions.assertEquals(newEmployeeDto.getFirstName(), result.getFirstName());

    }

    @Test
    @DisplayName("create employee ")
    public void createEmployee() {
        var createEmployeeDto = createMinRequiredCreateEmployeeDto();
        var employee = createMinRequiredEmployee(createEmployeeDto);
        var employeeDto = createMinRequiredEmployeeDto(employee);

        when(employeeRepository.existsByAccount(anyString())).thenReturn(false);
        when(employeeRepository.save(any())).thenReturn(employee);

        when(employeeMapper.create(createEmployeeDto)).
                thenReturn(employee);

        when(employeeMapper.map(employee)).thenReturn(employeeDto);


        var result = employeeService.create(createEmployeeDto);

        Assertions.assertEquals(employee.getAccount(), result.getAccount());
    }


    //создание сущности с минимально-необходимыми требованиями для сохранения
    public CreateEmployeeDto createMinRequiredCreateEmployeeDto() {
        return CreateEmployeeDto.
                builder().
                firstName("Иван").
                lastName("Романов").
                account("user").
                build();
    }
    //минимально-необходимый  Employee

    public Employee createMinRequiredEmployee(CreateEmployeeDto createEmployeeDto) {
        return Employee.
                builder().
                firstName(createEmployeeDto.getFirstName()).
                lastName(createEmployeeDto.getLastName()).
                account(createEmployeeDto.getAccount()).
                build();
    }

    //минимально-необходимый  EmployeeDto
    public EmployeeDto createMinRequiredEmployeeDto(Employee employee) {
        return EmployeeDto.
                builder().
                firstName(employee.getFirstName()).
                lastName(employee.getLastName()).
                account(employee.getAccount()).
                build();
    }
}

