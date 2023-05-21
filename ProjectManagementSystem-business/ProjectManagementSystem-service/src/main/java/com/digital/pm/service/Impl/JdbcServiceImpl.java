package com.digital.pm.service.Impl;

import com.digital.pm.service.JdbcService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class JdbcServiceImpl implements JdbcService {
    @Override
    public Connection getConnection(String url, String userName, String password) {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
