package com.digital.pm.repository.impl;

import com.digital.pm.dto.employee.FilterEmployee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.digital.pm.model.Employee;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorageImpl extends FileStorage {
    private final Path filePath = Path.of("ProjectManagementSystem-business/ProjectManagementSystem-repository/src/main/resources/data.txt");
    private final FileReader fileReader;
    private final FileWriter fileWriter;

    public FileStorageImpl() {
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


    @Override
    public synchronized Employee create(Employee employee) {
        try {
            employee.setId(getLastEmployeeId());
        } catch (IOException e) {
            throw new RuntimeException("error reading id field from file");
        }
        return writeObject(employee);


    }

    @Override
    public Employee update(Long employeeId, Employee employee) {

        try {
            var list = Files.readAllLines(filePath);

            for (int a = 0; a < list.size(); a++) {
                var employeeFromJList = getGson().fromJson(list.get(a), Employee.class);

                if (employeeFromJList.getId() == employeeId) {
                    employee.setId(employeeId);
                    var employeeFromArgument = getGson().toJson(employee);

                    list.set(a, employeeFromArgument);
                }
            }
            Files.write(filePath, list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return employee;
    }

    @Override
    public Employee getById(Long id) throws Exception {
        try {
            Gson gson = new Gson();
            var list = Files.readAllLines(filePath);
            for (String line : list) {
                var currentEmployee = gson.fromJson(line, Employee.class);
                if (currentEmployee.getId() == id) {
                    return currentEmployee;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new Exception(String.format("the employee with %s id is not found", id));

    }

    @Override
    public List<Employee> getAll() {
        Gson gson = new Gson();
        try {
            var list = Files.readAllLines(filePath);
            Type listOfMyClassObject = new TypeToken<ArrayList<Employee>>() {
            }.getType();

            return gson.fromJson(list.toString(), listOfMyClassObject);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Employee deleteById(Long id) throws Exception {
        var all = getAll();
        Gson gson = new Gson();
        var currentEmployee = getById(id);
        all.remove(currentEmployee);

        Files.write(filePath, all.stream().
                map(gson::toJson).collect(Collectors.toList()));

        return currentEmployee;
    }

    private Employee writeObject(Employee employee) {
        Gson gson = new Gson();
        try {
            fileWriter.write(gson.toJson(employee));
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
            var gson = new Gson();
            var list = Files.readAllLines(filePath);
            var object = gson.fromJson(list.get(list.size() - 1), Employee.class);

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

    @Override
    public List<Employee> searchWithFilter(FilterEmployee filterEmployee) {
        return getAll().
                stream().
                filter(x -> {
                    if (isNotNullObjects(x.getFirsName(), filterEmployee.getFirsName()) &&
                            x.getFirsName().equals(filterEmployee.getFirsName())) {
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
