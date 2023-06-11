package com.digital.pm.service.auth.impl;

import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.exceptions.BadRequest;
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
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        var employee = employeeRepository.findByCredential_Login(account).orElseThrow(
                () -> new BadRequest(String.format("the employee with %s login is not found", account))
        );


        return new User(employee.getCredential().getLogin(), employee.getCredential().getPassword(), Collections.emptyList());
    }
}
