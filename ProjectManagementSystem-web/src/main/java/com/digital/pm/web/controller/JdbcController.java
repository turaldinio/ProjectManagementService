package com.digital.pm.web.controller;

import com.digital.pm.service.JdbcService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.SQLException;


@Data
@AllArgsConstructor
public class JdbcController {
    private JdbcService jdbcService;

    public Connection getConnection(String url, String userName, String password)  {
        return jdbcService.getConnection(url, userName, password);
    }
}
