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
    @Column(name = "employee_id")
    private Long employeeId;
    @Column(name = "project_id")

    private Long projectId;
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role")
    private Role role;


}
