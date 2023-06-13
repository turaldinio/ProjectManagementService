package com.digital.pm.service.unit;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.credential.CreateCredentialDto;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.CredentialService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.impl.EmployeeServiceImpl;
import com.digital.pm.service.mapping.employee.EmployeeMapper;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class ServiceTest {
    @Spy
    private EmployeeRepository employeeRepository;
    @Spy
    private Logger employeeLogger;

    @Spy
    private CredentialService credentialService;
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

        when(employeeRepository.findByCredential_Login(anyString())).thenReturn(Optional.empty());

        var employee = createMinRequiredCreateEmployeeDto();
        employee.setCreateCredentialDto(CreateCredentialDto.
                builder().
                login("uesr").
                password("user").
                build());

        Assertions.assertThrows(BadRequest.class, () -> employeeService.findByAccount(employee.getCreateCredentialDto().getLogin()));

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
        when(employeeRepository.existsByCredential_Login(anyString())).thenReturn(false);
        when(employeeRepository.save(any())).thenReturn(newEmployee);

        when(employeeMapper.update(any(), any())).thenReturn(newEmployee);
        when(employeeMapper.map(newEmployee)).thenReturn(newEmployeeDto);

        var result = employeeService.update(1L, createMinRequiredCreateEmployeeDto());

        Assertions.assertEquals(newEmployeeDto.getFirstName(), result.getFirstName());

    }

    @Test
    @DisplayName("delete employee")
    public void delete() {
        var employee = createMinRequiredEmployee(createMinRequiredCreateEmployeeDto());
        employee.setStatus(EmployeeStatus.ACTIVE);

        var deletedEmployeeDto = createMinRequiredEmployeeDto(employee);
        deletedEmployeeDto.setStatus(EmployeeStatus.REMOTE);


        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);

        when(employeeMapper.map(employee)).thenReturn(deletedEmployeeDto);

        var result = employeeService.deleteById(1L);

        Assertions.assertEquals(employee.getStatus(), result.getStatus());


    }

    @Test
    @DisplayName("create employee ")
    public void createEmployee() {
        var createEmployeeDto = createMinRequiredCreateEmployeeDto();
        var employee = createMinRequiredEmployee(createEmployeeDto);
        var employeeDto = createMinRequiredEmployeeDto(employee);

        when(employeeRepository.existsByCredential_Login(anyString())).thenReturn(false);
        when(employeeRepository.save(any())).thenReturn(employee);

        when(employeeMapper.create(createEmployeeDto)).
                thenReturn(employee);

        when(employeeMapper.map(employee)).thenReturn(employeeDto);


        var result = employeeService.create(createEmployeeDto);

        Assertions.assertNotNull(result);
    }


    //создание сущности с минимально-необходимыми требованиями для сохранения
    public CreateEmployeeDto createMinRequiredCreateEmployeeDto() {
        return CreateEmployeeDto.
                builder().
                firstName("Иван").
                lastName("Романов").
                build();
    }
    // минимально-необходимый  Employee

    public Employee createMinRequiredEmployee(CreateEmployeeDto createEmployeeDto) {
        return Employee.
                builder().
                firstName(createEmployeeDto.getFirstName()).
                lastName(createEmployeeDto.getLastName()).
                email(createEmployeeDto.getEmail()).
                patronymic(createEmployeeDto.getPatronymic()).
                post(createEmployeeDto.getPost()).
                build();
    }

    //минимально-необходимый  EmployeeDto
    public EmployeeDto createMinRequiredEmployeeDto(Employee employee) {
        return EmployeeDto.
                builder().
                firstName(employee.getFirstName()).
                lastName(employee.getLastName()).
                build();
    }
}


