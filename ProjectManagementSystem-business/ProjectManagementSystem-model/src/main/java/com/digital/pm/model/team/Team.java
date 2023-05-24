package com.digital.pm.model.team;

import com.digital.pm.common.enums.Role;
import com.digital.pm.model.employee.Employee;
import com.digital.pm.model.project.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<Employee> employees;

    @ManyToMany
    private List<Project> project;
    @Enumerated(EnumType.STRING)
    private Role roles;


}
