package com.digital.pm.service.auth;

import com.digital.pm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        var employee = employeeService.findByEmployeeAccount(account);


        return new User(employee.getAccount(), employee.getPassword(), Collections.emptyList());
    }
}
