package com.digital.pm.repository.impl;

import com.digital.pm.repository.DataStorage;
import com.google.gson.Gson;
import pm.model.Employee;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStorageImpl implements DataStorage {
    private AtomicInteger atomicInteger = new AtomicInteger();
    private final Path filePath = Path.of("ProjectManagementSystem-business/ProjectManagementSystem-repository/src/main/resources/data.txt");
    private FileReader fileReader;
    private FileWriter fileWriter;

    public DataStorageImpl() {
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
    public Employee create(Employee employee) {
        try {
            employee.setId(getEmployeeId());
        } catch (IOException e) {
            throw new RuntimeException("error reading id field from file");
        }
        return writeObject(employee);


    }

    @Override
    public Employee update(int employeeId, Employee employee) {
        Gson gson = new Gson();
        try {
            var list = Files.readAllLines(filePath);

            for (int a = 0; a < list.size(); a++) {
                var employeeFromJList = gson.fromJson(list.get(a), Employee.class);

                if (employeeFromJList.getId() == employeeId) {
                    employee.setId(employeeId);
                    var employeeFromArgument = gson.toJson(employee);

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
    public Employee getById(int id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public void deleteById(int id) {

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

    public int getEmployeeId() throws IOException {
        if (!fileReader.ready()) {
            return 0;
        } else {
            var gson = new Gson();
            var list = Files.readAllLines(filePath);
            var object = gson.fromJson(list.get(list.size() - 1), Employee.class);

            atomicInteger.set(object.getId());

            return atomicInteger.incrementAndGet();
        }

    }

    public void createDataStorageFile() {
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
