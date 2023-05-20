package com.digital.pm.service.Impl;

import com.digital.pm.service.JdbcService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcServiceImpl implements JdbcService {
    @Override
    public Connection getConnection(String url, String userName, String password) throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }
}