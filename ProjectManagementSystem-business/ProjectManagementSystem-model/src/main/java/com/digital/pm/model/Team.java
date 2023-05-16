package com.digital.pm.model;

import com.digital.pm.common.enums.Roles;

import java.util.Map;

public class Team {
    private Long id;
    private Map<Employee, Roles> employees;

    private Project project;


}
