package com.digital.pm.model;

import com.digital.pm.common.enums.Roles;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

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
