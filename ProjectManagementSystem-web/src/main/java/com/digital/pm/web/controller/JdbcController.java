package com.digital.pm.web.controller;

import com.digital.pm.service.JdbcService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.SQLException;


@Controller
@RequiredArgsConstructor
public class JdbcController {
    private final JdbcService jdbcService;

    public Connection getConnection(String url, String userName, String password)  {
        return jdbcService.getConnection(url, userName, password);
    }
}
