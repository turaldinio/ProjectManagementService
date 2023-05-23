package com.digital.pm.app;

import com.digital.pm.repository.EmployeeDataRepository;
import com.digital.pm.repository.impl.EmployeeDataJdbcRepositoryImpl;
import com.digital.pm.repository.impl.EmployeeDataFileRepositoryImpl;
import com.digital.pm.service.EmployeeDataService;
import com.digital.pm.service.impl.EmployeeDataServiceImpl;
import com.digital.pm.service.mapping.EmployeeMapper;
import com.digital.pm.service.mapping.ProjectMapper;
import com.digital.pm.service.mapping.TaskMapper;
import com.digital.pm.service.mapping.TeamMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
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
    public EmployeeDataRepository dBRepository() throws SQLException {
        return new EmployeeDataJdbcRepositoryImpl(getConnection());
    }

    @Bean
    public EmployeeDataRepository fileRepository() throws IOException {
        return new EmployeeDataFileRepositoryImpl(defaultFilePathRepository());
    }

    @Bean
    public EmployeeMapper employeeMapper() {
        return new EmployeeMapper();
    }

    @Bean
    public ProjectMapper projectMapper() {
        return new ProjectMapper();
    }

    @Bean
    public TaskMapper taskMapper() {
        return new TaskMapper();
    }

    @Bean
    public TeamMapper teamMapper() {
        return new TeamMapper();
    }

    @Bean
    public EmployeeDataService dataBaseFileService() throws IOException {
        return new EmployeeDataServiceImpl(employeeMapper(), fileRepository());
    }

    @Bean
    public EmployeeDataService dataBaseService() throws SQLException {
        return new EmployeeDataServiceImpl(employeeMapper(), dBRepository());
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
