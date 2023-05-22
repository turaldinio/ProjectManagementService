package com.digital.pm.repository.impl;

import com.digital.pm.repository.EmployeeDataRepository;
import com.google.gson.Gson;


public abstract class FileStorage implements EmployeeDataRepository {
    private final Gson gson;

    public FileStorage() {
        gson = new Gson();
    }

    public Gson getGson() {
        return gson;
    }
}
