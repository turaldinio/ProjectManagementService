package com.digital.pm.repository.impl;

import com.digital.pm.common.filters.EmployeeFilter;
import com.google.gson.reflect.TypeToken;
import com.digital.pm.model.Employee;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmployeeDataFileRepositoryImpl extends FileStorage {
    private final Path filePath;
    private final FileReader fileReader;
    private final FileWriter fileWriter;

    public EmployeeDataFileRepositoryImpl(String path) throws IOException {
        filePath = Path.of(path);
        if (Files.notExists(Path.of(filePath.toString()))) {
            createDataStorageFile();
        }
        fileReader = new FileReader(filePath.toFile());
        fileWriter = new FileWriter(filePath.toFile(), true);


    }

    @Override
    public synchronized Employee create(Employee employee) throws Exception {
        employee.setId(getLastEmployeeId());
        return writeObject(employee);


    }

    @Override
    public Employee update(Long employeeId, Employee employee) throws Exception {

        var list = Files.
                readAllLines(filePath).
                stream().
                map(x -> getGson().fromJson(x, Employee.class)).
                map(x -> {
                    if (x.getId().longValue() == employeeId) {
                        employee.setId(employeeId);
                        return getGson().toJson(employee);
                    }
                    return getGson().toJson(x);
                }).toList();

        Files.write(filePath, list);
        return employee;
    }

    @Override
    public Employee getById(Long id) throws Exception {
        return Files.readAllLines(filePath).
                stream().
                map(x -> getGson().fromJson(x, Employee.class)).
                filter(x -> x.getId().longValue() == id).
                findFirst().
                orElseThrow(() ->
                        new Exception(String.format("the user with %s id is not found", id)));

    }

    @Override
    public List<Employee> getAll() throws Exception {
        var list = Files.readAllLines(filePath);
        Type listOfMyClassObject = new TypeToken<ArrayList<Employee>>() {
        }.getType();

        return getGson().fromJson(list.toString(), listOfMyClassObject);

    }

    @Override
    public Employee deleteById(Long id) throws Exception {
        var all = getAll();
        var currentEmployee = getById(id);
        all.remove(currentEmployee);

        Files.write(filePath, all.stream().
                map(getGson()::toJson).collect(Collectors.toList()));

        return currentEmployee;
    }

    private Employee writeObject(Employee employee) throws IOException {
        fileWriter.write(getGson().toJson(employee));
        fileWriter.write("\n");
        fileWriter.flush();

        return employee;
    }


    public long getLastEmployeeId() throws Exception {
        if (!fileReader.ready()) {
            return 0;
        } else {
            var list = Files.readAllLines(filePath);
            var object = getGson().fromJson(list.get(list.size() - 1), Employee.class);

            return object.getId() + 1;

        }

    }

    public void createDataStorageFile() throws IOException {
            Files.createFile(filePath);
    }

    @Override
    public List<Employee> searchWithFilter(EmployeeFilter filterEmployee) throws Exception {
        return getAll().
                stream().
                filter(x -> {
                    if (isNotNullObjects(x.getFirstName(), filterEmployee.getFirstName()) &&
                            x.getFirstName().equals(filterEmployee.getFirstName())) {
                        return true;
                    }
                    if (isNotNullObjects(x.getLastName(), filterEmployee.getLastName()) &&
                            x.getLastName().equals(filterEmployee.getLastName())) {
                        return true;
                    }
                    if (isNotNullObjects(x.getPatronymic(), filterEmployee.getPatronymic()) && x.getPatronymic().equals(filterEmployee.getPatronymic())) {
                        return true;
                    }
                    if (isNotNullObjects(x.getEmail(), filterEmployee.getEmail()) && x.getEmail().equals(filterEmployee.getEmail())) {
                        return true;
                    }

//                    if (isNotNullObjects(x.getAccount(), filterEmployee.getAccount()) && x.getAccount().equals(filterEmployee.getAccount())) {
//                        return true;
//                    }

                    return false;
                }).collect(Collectors.toList());

    }

    public boolean isNotNullObjects(Object first, Object second) {
        return Objects.nonNull(first) && Objects.nonNull(second);
    }
}
