package com.digital.pm.service.impl;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.Credential;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.spec.EmployeeSpecification;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.CredentialService;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("employeeLogger")
    private Logger logger;

    @Transactional
    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        logger.info("create method has started");
        if (!checkRequiredValue(createEmployeeDto)) {//проверка обязательных полей
            logger.info("canceling the creation operation");

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

        logger.info(String.format("employee %s has been saved", createEmployeeDto));

        return employeeMapper.map(employee);
    }

    @Transactional
    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        logger.info("update method has started");

        var employee = employeeRepository.findById(employeeId).orElseThrow(() -> invalidId(employeeId));
        logger.info(String.format("employee with %d is found", employeeId));

        var newEmployee = employeeMapper.update(employee, createEmployeeDto);//обновляем все кроме уд

        logger.info(String.format("created new employee %s", newEmployee));

        //проверка обязательных полей имя/фамилия
        if (!checkRequiredValue(newEmployee)) {
            logger.info("canceling the update operation");
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
        logger.info("credentials are installed");

        employeeRepository.save(newEmployee);

        logger.info(String.format("employee %s has been saved", newEmployee));

        return employeeMapper.map(newEmployee);

    }

    @Override
    public EmployeeDto getById(Long id) {
        logger.info("getById method has started");

        logger.info(String.format("search for a user with id %d", id));

        return employeeMapper.
                map(employeeRepository.
                        findById(id).
                        orElseThrow(() ->
                                {
                                    logger.info("canceling the getById operation");
                                    return invalidId(id);
                                }
                        ));
    }

    @Transactional
    @Override
    public EmployeeDto deleteById(Long id) {
        logger.info("deleteById method has started");

        logger.info(String.format("deleting user with id %d", id));

        var currentEmployee = employeeRepository.
                findById(id).
                orElseThrow(() -> invalidId(id));

        if (currentEmployee.getStatus().equals(EmployeeStatus.REMOTE)) {
            logger.info("canceling the deleteById operation");

            throw invalidEmployeeAlreadyRemoved(id);
        }

        currentEmployee.setStatus(EmployeeStatus.REMOTE);
        logger.info("changed employee status ACTIVE -> REMOTE");

        var result = employeeRepository.save(currentEmployee);
        logger.info(String.format("employee %s has been saved", currentEmployee));

        return employeeMapper.map(result);
    }

    @Override
    public Boolean existsById(Long executorId) {
        logger.info("existsById method has started");

        return employeeRepository.existsById(executorId);
    }

    public List<EmployeeDto> findAll() {
        logger.info("findAll method has started");

        logger.info("search for all employees");
        return employeeMapper.map(employeeRepository.findAll());
    }

    public List<EmployeeDto> findAll(EmployeeFilter employeeFilter) {
        logger.info("findAll with filter method has started");

        logger.info(String.format("search for all employees by filter %s", employeeFilter));

        var result = employeeRepository.
                findAll(EmployeeSpecification.getSpec(employeeFilter));
        logger.info("employees successfully found");

        return employeeMapper.map(result);
    }


    @Override
    public EmployeeDto findByAccount(String account) {
        logger.info("findByAccount method has started");

        logger.info(String.format("search for an employee by account %s ", account));

        return employeeMapper.map(employeeRepository.findByCredential_Login(account).orElseThrow(
                () -> {
                    logger.info("canceling the findByAccount operation");

                    return invalidAccountNotFound(account);
                }

        ));

    }

    public boolean checkRequiredValue(CreateEmployeeDto createEmployeeDto) {
        logger.info("checking required fields for a employee");

        return
                Objects.nonNull(createEmployeeDto.getFirstName()) &&
                        Objects.nonNull(createEmployeeDto.getLastName()) &&
                        !createEmployeeDto.getLastName().isBlank() &&
                        !createEmployeeDto.getFirstName().isBlank();
    }

    public boolean checkRequiredValue(Employee newEmployee) {
        logger.info("checking required fields for a employee");
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
