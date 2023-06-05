package com.digital.pm.model;

import com.digital.pm.common.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//первичный ключ
    @Column(nullable = false,
            name = "first_name")
    private String firstName;//имя
    @Column(nullable = false,
            name = "last_name")
    private String lastName;//фамилия
    @Column(name = "credential_id")
    private Long credentialId;//данные учетной записи (логин/пароль)
    private String patronymic;//отчество
    private String post;//должность

    private String email;//адрес электронной почты
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;//статус сотрудника удаленный/активный


}
