package com.digital.pm.service.impl;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.spec.EmployeeSpecification;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {

        //проверка обязательных параметров
        if (!checkRequiredValue(createEmployeeDto)) {
            throw new BadRequest("employee firstname or lastname cannot be null or blank");
        }

        //проверка;если указан уз пароль обязателен.
        //проверка уникальности уз
//        if (employeeRepository.existsByCredential_Login(createEmployeeDto.getAccount())) {
//            throw invalidAccount(createEmployeeDto.getAccount());
//        }
//        if (createEmployeeDto.getAccount() != null) {
//            checkPassword(createEmployeeDto);
//
//        }

        var employee = employeeMapper.create(createEmployeeDto);
        employeeRepository.save(employee);

        return employeeMapper.map(employee);
    }


    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        var employee = employeeRepository.findById(employeeId).orElseThrow(() -> invalidId(employeeId));

        //если уз обновляется, проверяем что он уникальный
//        if (createEmployeeDto.getAccount() != null && employeeRepository.existsByCredential_Login(createEmployeeDto.getAccount())) {
//            throw invalidAccount(createEmployeeDto.getAccount());
//        }
//
        var newEmployee = employeeMapper.update(employee, createEmployeeDto);
//
//        //проверка, после обновления поле password!=null если есть уз
//        if (newEmployee.getAccount() != null) {
//            checkPassword(newEmployee);
//        }

        if (!checkRequiredValue(newEmployee)) {
            throw new BadRequest("employee firstname or lastname cannot be null or blank");
        }


        employeeRepository.save(newEmployee);

        return employeeMapper.map(newEmployee);
    }

    @Override
    public EmployeeDto getById(Long id) {
        return employeeMapper.
                map(employeeRepository.
                        findById(id).
                        orElseThrow(() -> invalidId(id)));
    }

    @Override
    public EmployeeDto deleteById(Long id) {
        var currentEmployee = employeeRepository.
                findById(id).
                orElseThrow(() -> invalidId(id));

        if (currentEmployee.getStatus().equals(EmployeeStatus.REMOTE)) {
            throw new BadRequest(String.format("the user with %d id has already been deleted", id));
        }

        currentEmployee.setStatus(EmployeeStatus.REMOTE);
        var result = employeeRepository.save(currentEmployee);

        return employeeMapper.map(result);
    }

    @Override
    public Boolean existsById(Long executorId) {
        return employeeRepository.existsById(executorId);
    }

    public List<EmployeeDto> findAll() {
        return employeeMapper.map(employeeRepository.findAll());
    }

    public List<EmployeeDto> findAll(EmployeeFilter employeeFilter) {
        var result = employeeRepository.
                findAll(EmployeeSpecification.getSpec(employeeFilter));

        return employeeMapper.map(result);
    }

    public BadRequest invalidId(Long id) {
        return new BadRequest(String.format("the employee with %d id is not found", id));
    }

    public BadRequest invalidAccount(String account) {
        return new BadRequest(String.format("the %s account  is already exists ", account));
    }

    public BadRequest invalidAccountAndPassword() {
        return new BadRequest("the password cannot be empty or blank");

    }

    @Override
    public EmployeeDto findByAccount(String account) {
//        return employeeMapper.map(employeeRepository.findByCredential_Login(account).orElseThrow(
//                () -> new BadRequest(String.format("the employee with %s account not found", account))));
        return null;
    }

    public boolean checkRequiredValue(CreateEmployeeDto createEmployeeDto) {
        return createEmployeeDto.getFirstName() != null &&
                createEmployeeDto.getLastName() != null &&
                !createEmployeeDto.getLastName().isBlank() &&
                !createEmployeeDto.getFirstName().isBlank();
    }

    public boolean checkRequiredValue(Employee newEmployee) {
        return newEmployee.getFirstName() != null &&
                newEmployee.getLastName() != null &&
                !newEmployee.getLastName().isBlank() &&
                !newEmployee.getFirstName().isBlank();
    }

    public void checkPassword(CreateEmployeeDto createEmployeeDto) {
        if (!createEmployeeDto.getPassword().isBlank() && !createEmployeeDto.getPassword().isEmpty()) {
            return;
        }
        throw invalidAccountAndPassword();
    }

    public void checkPassword(Employee employee) {
//        if (!employee.getPassword().isBlank() && !employee.getPassword().isEmpty()) {
//            return;
//        }
        throw invalidAccountAndPassword();
    }
}
