package com.digital.pm.repository.impl;

import com.digital.pm.common.filters.EmployeeFilter;
import com.google.gson.reflect.TypeToken;
import com.digital.pm.model.employee.Employee;

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

    public EmployeeDataFileRepositoryImpl(String path) {
        filePath = Path.of(path);
        try {
            if (Files.notExists(Path.of(filePath.toString()))) {
                createDataStorageFile();
            }
            fileReader = new FileReader(filePath.toFile());
            fileWriter = new FileWriter(filePath.toFile(), true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public synchronized Employee create(Employee employee) {
        try {
            employee.setId(getLastEmployeeId());
        } catch (IOException e) {
            throw new RuntimeException("error reading id field from file");
        }
        return writeObject(employee);


    }

    public Employee update(Long employeeId, Employee employee) {

        try {
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return employee;
    }

    public Employee getById(Long id) throws Exception {
        try {
            return Files.readAllLines(filePath).
                    stream().
                    map(x -> getGson().fromJson(x, Employee.class)).
                    filter(x -> x.getId().longValue() == id).
                    findFirst().
                    orElseThrow(() ->
                            new Exception(String.format("the user with %s id is not found", id)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Employee> getAll() {

        try {
            var list = Files.readAllLines(filePath);
            Type listOfMyClassObject = new TypeToken<ArrayList<Employee>>() {
            }.getType();

            return getGson().fromJson(list.toString(), listOfMyClassObject);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Employee deleteById(Long id) throws Exception {
        var all = getAll();
        var currentEmployee = getById(id);
        all.remove(currentEmployee);

        Files.write(filePath, all.stream().
                map(getGson()::toJson).collect(Collectors.toList()));

        return currentEmployee;
    }

    private Employee writeObject(Employee employee) {

        try {
            fileWriter.write(getGson().toJson(employee));
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    public long getLastEmployeeId() throws IOException {
        if (!fileReader.ready()) {
            return 0;
        } else {
            var list = Files.readAllLines(filePath);
            var object = getGson().fromJson(list.get(list.size() - 1), Employee.class);

            return object.getId() + 1;

        }

    }

    public void createDataStorageFile() {
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> searchWithFilter(EmployeeFilter filterEmployee) {
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
                    if (isNotNullObjects(x.getPost(), filterEmployee.getPost()) && x.getPost().equals(filterEmployee.getPost())) {
                        return true;
                    }
                    if (isNotNullObjects(x.getAccount(), filterEmployee.getAccount()) && x.getAccount().equals(filterEmployee.getAccount())) {
                        return true;
                    }
                    if (isNotNullObjects(x.getStatus(), filterEmployee.getStatus()) && x.getStatus() == (filterEmployee.getStatus())) {
                        return true;
                    }
                    if (isNotNullObjects(x.getId(), filterEmployee.getId()) && x.getId().longValue() == filterEmployee.getId().longValue()) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());

    }

    public boolean isNotNullObjects(Object first, Object second) {
        return Objects.nonNull(first) && Objects.nonNull(second);
    }
}
