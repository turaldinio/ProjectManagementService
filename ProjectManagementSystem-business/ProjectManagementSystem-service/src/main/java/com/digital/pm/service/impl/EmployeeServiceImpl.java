package com.digital.pm.service.impl;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.Credential;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.spec.EmployeeSpecification;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CredentialService credentialService;
    @Autowired
    private Logger logger;

    @Transactional
    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {

        if (!checkRequiredValue(createEmployeeDto)) {//проверка обязательных полей
            throw invalidRequiredFields();
        }

        if (Objects.nonNull(createEmployeeDto.getCreateCredentialDto())) {//проверка. Если учетные данные(уд) создаются

            var employee = employeeMapper.create(createEmployeeDto, credentialService.//создаем сотрудника с уд
                    create(createEmployeeDto.getCreateCredentialDto()));

            employeeRepository.save(employee);

            return employeeMapper.map(employee);
        }

        Employee employee = employeeMapper.create(createEmployeeDto);//иначе создаем сотрудника без уд
        employeeRepository.save(employee);

        return employeeMapper.map(employee);
    }

    @Transactional
    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        var employee = employeeRepository.findById(employeeId).orElseThrow(() -> invalidId(employeeId));

        var newEmployee = employeeMapper.update(employee, createEmployeeDto);//обновляем все кроме уд

        //проверка обязательных полей имя/фамилия
        if (!checkRequiredValue(newEmployee)) {
            throw invalidRequiredFields();
        }

        Credential credential = employee.getCredential();//создаем пустые уд

        //если в запросе нам передали уд на обновление
        if (Objects.nonNull(createEmployeeDto.getCreateCredentialDto())) {
            if (Objects.isNull(credential)) {//если уд у нас не было
                credential = credentialService.create(createEmployeeDto.getCreateCredentialDto());//создаем уд со всеми проверками (логин/пароль)
            } else {
                //если уд у нас был
                credential = credentialService.update(employee.getCredential(), createEmployeeDto.getCreateCredentialDto());//получаем обновленный уд

            }
        }
        newEmployee.setCredential(credential); // сетим обновленный|созданный|старый уд в обновленный объект Employee

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

    @Transactional
    @Override
    public EmployeeDto deleteById(Long id) {
        var currentEmployee = employeeRepository.
                findById(id).
                orElseThrow(() -> invalidId(id));

        if (currentEmployee.getStatus().equals(EmployeeStatus.REMOTE)) {
            throw invalidEmployeeAlreadyRemoved(id);
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
        logger.warn("warn");
        logger.info("info ");
        logger.debug("debug ");
        return employeeMapper.map(employeeRepository.findAll());
    }

    public List<EmployeeDto> findAll(EmployeeFilter employeeFilter) {
        var result = employeeRepository.
                findAll(EmployeeSpecification.getSpec(employeeFilter));

        return employeeMapper.map(result);
    }


    @Override
    public EmployeeDto findByAccount(String account) {
        return employeeMapper.map(employeeRepository.findByCredential_Login(account).orElseThrow(
                () -> invalidAccountNotFound(account)));

    }

    public boolean checkRequiredValue(CreateEmployeeDto createEmployeeDto) {
        return
                Objects.nonNull(createEmployeeDto.getFirstName()) &&
                        Objects.nonNull(createEmployeeDto.getLastName()) &&
                        !createEmployeeDto.getLastName().isBlank() &&
                        !createEmployeeDto.getFirstName().isBlank();
    }

    public boolean checkRequiredValue(Employee newEmployee) {
        return Objects.nonNull(newEmployee.getFirstName()) &&
                Objects.nonNull(newEmployee.getLastName()) &&
                !newEmployee.getLastName().isBlank() &&
                !newEmployee.getFirstName().isBlank();
    }


    public BadRequest invalidId(Long id) {
        return new BadRequest(String.format("the employee with %d id is not found", id));
    }

    public BadRequest invalidEmployeeAlreadyRemoved(Long id) {
        return new BadRequest(String.format("the %d account is already exists ", id));
    }

    public BadRequest invalidRequiredFields() {
        throw new BadRequest("employee firstname or lastname cannot be null or blank");
    }

    public BadRequest invalidAccountNotFound(String account) {
        return new BadRequest(String.format("the %s account is not found ", account));
    }
}
