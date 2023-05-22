package com.digital.pm.model;

import com.digital.pm.common.enums.Roles;
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
    // TODO: 22.05.2023 Вопрос о связи Employee-Roles Map?
    @OneToMany
    private List<Employee> employees;
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @OneToOne
    private Project project;


}
