package com.digital.pm.repository.impl;

import com.digital.pm.repository.DataStorage;
import pm.model.Employee;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStorageImpl implements DataStorage {
    private AtomicInteger atomicInteger = new AtomicInteger();
    private final String filePath = "ProjectManagementSystem-business/ProjectManagementSystem-repository/src/main/resources/data.txt";
    private FileReader fileReader;
    private FileWriter fileWriter;

    public DataStorageImpl() {
        try {
            if (Files.notExists(Path.of(filePath))) {
                Files.createFile(Path.of(filePath));
            }
            fileReader = new FileReader(filePath);
            fileWriter = new FileWriter(filePath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public Employee create(Employee employee) {
        employee.setId(atomicInteger.incrementAndGet());
        return writeObject(employee);


    }

    @Override
    public Employee update() {
        return null;
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
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append(employee.getId()).append(" ").
                append(employee.getFirsName()).append(" ").
                append(employee.getLastName()).append(" ").
                append(employee.getPatronymic()).append(" ").
                append(employee.getPost()).append(" ").
                append(employee.getAccount()).append(" ").
                append(employee.getEmail()).append(" ");
        try {
            fileWriter.write(stringBuilder.toString());
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }
}
