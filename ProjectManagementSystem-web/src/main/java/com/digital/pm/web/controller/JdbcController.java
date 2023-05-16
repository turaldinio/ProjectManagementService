package com.digital.pm.web.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class JdbcController {
    private String url;
    private String user;
    private String password;

    public Connection getConnection()  {
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
