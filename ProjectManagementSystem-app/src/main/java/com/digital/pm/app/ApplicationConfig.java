package com.digital.pm.app;

import com.digital.pm.repository.DataBaseRepository;
import com.digital.pm.repository.impl.DataBaseJdbcImpl;
import com.digital.pm.repository.impl.DataBaseFileImpl;
import com.digital.pm.service.DataBaseService;
import com.digital.pm.service.Impl.DataBaseServiceImpl;
import com.digital.pm.service.mapping.EmployeeMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Configuration
public class ApplicationConfig {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataBaseRepository dBRepository() throws SQLException {
        return new DataBaseJdbcImpl(getConnection());
    }

    @Bean
    public DataBaseRepository fileRepository() {
        return new DataBaseFileImpl(defaultFilePathRepository());
    }

    @Bean
    public DataBaseService dataBaseFileService() {
        return new DataBaseServiceImpl(employeeMapper(), fileRepository());
    }

    @Bean
    public DataBaseService dataBaseService() throws SQLException {
        return new DataBaseServiceImpl(employeeMapper(), dBRepository());
    }

    @Bean
    public EmployeeMapper employeeMapper() {
        return new EmployeeMapper();
    }

    @Bean
    String defaultFilePathRepository() {
        return "ProjectManagementSystem-business/ProjectManagementSystem-repository/src/main/resources/data.txt";
    }

    @Bean
    public Connection getConnection() throws SQLException {
        return DriverManager.
                getConnection(
                        url, user, password);
    }


}
