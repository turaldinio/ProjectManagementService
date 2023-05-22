package com.digital.pm.model;

import com.digital.pm.common.enums.Roles;
import com.digital.pm.model.employee.Employee;
import com.digital.pm.model.project.Project;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Employee> employees;
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @OneToOne
    private Project project;


}
