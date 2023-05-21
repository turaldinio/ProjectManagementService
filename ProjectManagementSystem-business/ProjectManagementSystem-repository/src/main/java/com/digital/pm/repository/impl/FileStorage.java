package com.digital.pm.repository.impl;

import com.digital.pm.repository.DataStorage;
import com.google.gson.Gson;

public abstract class FileStorage  {
    private Gson gson;

    public FileStorage() {
        gson = new Gson();
    }

    public Gson getGson() {
        return gson;
    }
}
