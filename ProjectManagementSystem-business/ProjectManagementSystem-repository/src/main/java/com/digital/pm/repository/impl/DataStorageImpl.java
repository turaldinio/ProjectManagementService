package com.digital.pm.repository.impl;

import com.digital.pm.repository.DataStorage;
import pm.model.Employee;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStorageImpl implements DataStorage {
    private AtomicInteger atomicInteger = new AtomicInteger();

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
