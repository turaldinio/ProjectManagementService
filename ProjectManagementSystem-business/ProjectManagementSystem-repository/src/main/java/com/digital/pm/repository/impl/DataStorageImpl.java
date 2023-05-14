package com.digital.pm.repository.impl;

import com.digital.pm.repository.DataStorage;
import pm.model.Employee;

import java.io.BufferedReader;
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
            fileWriter = new FileWriter(filePath,true);

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

        try {
            fileWriter.write(employee.toString());
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
            var list = Files.readAllLines(Path.of(filePath));
            atomicInteger.set(Integer.parseInt(list.get(list.size() - 1).split(" ")[0]));

            return atomicInteger.incrementAndGet();
        }

    }
}
