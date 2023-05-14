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
    private String filePath = "src/main/resources/data.txt";
    private FileReader fileReader;
    private FileWriter fileWriter;

    public DataStorageImpl() {
        if (Files.notExists(Path.of(filePath))) {
            try {
                Files.createFile(Path.of(filePath));

                fileReader = new FileReader(filePath);
                fileWriter = new FileWriter(filePath);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Employee create(Employee employee) {
        employee.setId(atomicInteger.incrementAndGet());
        // TODO: 14.05.2023 логика сериализации
        return null;
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
}
