package com.digital.pm.service.impl;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.employee.EmployeeDtoFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.Credential;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.spec.EmployeeSpecification;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.CredentialService;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.employee.EmployeeFilterMapping;
import com.digital.pm.service.mapping.employee.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CredentialService credentialService;
    private final EmployeeFilterMapping employeeFilterMapping;

    @Transactional
    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        log.info("create method has started");
        if (checkRequiredValue(createEmployeeDto)) {//проверка обязательных полей
            log.info("canceling the creation operation");

            throw invalidRequiredFields();
        }
        Employee employee;

        if (Objects.nonNull(createEmployeeDto.getCreateCredentialDto())) {//проверка. Если учетные данные(уд) создаются
            employee = employeeMapper.create(createEmployeeDto, credentialService.//создаем сотрудника с уд
                    create(createEmployeeDto.getCreateCredentialDto()));
        } else {
            employee = employeeMapper.create(createEmployeeDto);//иначе создаем сотрудника без уд

        }
        employeeRepository.save(employee);

        log.info(String.format("employee %s has been saved", createEmployeeDto));

        return employeeMapper.map(employee);
    }

    @Transactional
    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        log.info("update method has started");

        var employee = employeeRepository.findById(employeeId).orElseThrow(() -> invalidId(employeeId));
        log.info(String.format("employee with %d is found", employeeId));

        var newEmployee = employeeMapper.update(employee, createEmployeeDto);//обновляем все кроме уд

        log.info(String.format("created new employee %s", newEmployee));

        //проверка обязательных полей имя/фамилия
        if (checkRequiredValue(newEmployee)) {
            log.info("canceling the update operation");
            throw invalidRequiredFields();
        }

        Credential credential = employee.getCredential();//создаем пустые уд

        //если в запросе нам передали уд на обновление
        if (!ObjectUtils.isEmpty(createEmployeeDto.getCreateCredentialDto())) {
            if (ObjectUtils.isEmpty(credential)) {//если уд у нас не было
                credential = credentialService.create(createEmployeeDto.getCreateCredentialDto());//создаем уд со всеми проверками (логин/пароль)
            } else {
                //если уд у нас был
                credential = credentialService.update(employee.getCredential(), createEmployeeDto.getCreateCredentialDto());//получаем обновленный уд

            }
        }
        newEmployee.setCredential(credential); // сетим обновленный|созданный|старый уд в обновленный объект Employee
        log.info("credentials are installed");

        employeeRepository.save(newEmployee);

        log.info(String.format("employee %s has been saved", newEmployee));

        return employeeMapper.map(newEmployee);

    }

    @Override
    public EmployeeDto getById(Long id) {
        log.info("getById method has started");

        log.info(String.format("search for a user with id %d", id));

        return employeeMapper.
                map(employeeRepository.
                        findById(id).
                        orElseThrow(() ->
                                {
                                    log.info("canceling the getById operation");
                                    return invalidId(id);
                                }
                        ));
    }

    @Transactional
    @Override
    public EmployeeDto deleteById(Long id) {
        log.info("deleteById method has started");

        log.info(String.format("deleting user with id %d", id));

        var currentEmployee = employeeRepository.
                findById(id).
                orElseThrow(() -> invalidId(id));

        if (currentEmployee.getStatus().equals(EmployeeStatus.REMOTE)) {
            log.info("canceling the deleteById operation");

            throw invalidEmployeeAlreadyRemoved(id);
        }

        currentEmployee.setStatus(EmployeeStatus.REMOTE);
        log.info("changed employee status ACTIVE -> REMOTE");

        var result = employeeRepository.save(currentEmployee);
        log.info(String.format("employee %s has been saved", currentEmployee));

        return employeeMapper.map(result);
    }

    @Override
    public Boolean existsById(Long executorId) {
        log.info("existsById method has started");

        return employeeRepository.existsById(executorId);
    }

    public List<EmployeeDto> findAll() {
        log.info("findAll method has started");

        log.info("search for all employees");
        return employeeMapper.map(employeeRepository.findAll());
    }

    public List<EmployeeDto> findAll(EmployeeDtoFilter employeeFilter) {
        log.info("findAll with filter method has started");

        log.info("mapping EmployeeDtoFilter to EmployeeDaoFilter");

        var employeeDaoFilter = employeeFilterMapping.create(employeeFilter);

        log.info(String.format("search for all employees by filter %s", employeeDaoFilter));

        var result = employeeRepository.
                findAll(EmployeeSpecification.getSpec(employeeDaoFilter));
        log.info("employees successfully found");

        return employeeMapper.map(result);
    }


    @Override
    public EmployeeDto findByAccount(String account) {
        log.info("findByAccount method has started");

        log.info(String.format("search for an employee by account %s ", account));

        return employeeMapper.map(employeeRepository.findByCredential_Login(account).orElseThrow(
                () -> {
                    log.info("canceling the findByAccount operation");

                    return invalidAccountNotFound(account);
                }

        ));

    }

    public boolean checkRequiredValue(CreateEmployeeDto createEmployeeDto) {
        log.info("checking required fields for a employee");

        return
                ObjectUtils.isEmpty(createEmployeeDto.getFirstName()) ||
                        ObjectUtils.isEmpty(createEmployeeDto.getLastName());
    }

    public boolean checkRequiredValue(Employee newEmployee) {
        log.info("checking required fields for a employee");
        return ObjectUtils.isEmpty(newEmployee.getFirstName()) ||
                ObjectUtils.isEmpty(newEmployee.getLastName());
    }


    public BadRequest invalidId(Long id) {
        return new BadRequest(String.format("the employee with %d id is not found", id));
    }

    public BadRequest invalidEmployeeAlreadyRemoved(Long id) {
        return new BadRequest(String.format("the %d account is already deleted ", id));
    }

    public BadRequest invalidRequiredFields() {
        throw new BadRequest("employee firstname or lastname cannot be null or blank");
    }

    public BadRequest invalidAccountNotFound(String account) {
        return new BadRequest(String.format("the %s account is not found ", account));
    }
}
