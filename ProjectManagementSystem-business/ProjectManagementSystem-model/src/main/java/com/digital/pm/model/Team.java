package com.digital.pm.model;

import com.digital.pm.common.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//первичный ключ команды
    @Column(name = "employee_id")
    private Long employeeId;//id участника команды
    @Column(name = "project_id")

    private Long projectId;//id проекта
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role")
    private Role role;//роль, которую сотрудник занимает в этом проекте


}
