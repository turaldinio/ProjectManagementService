package com.digital.pm.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcService {
    Connection getConnection(String url, String userName, String password) ;
}
