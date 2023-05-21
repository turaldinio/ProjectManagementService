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
    private Long id;
    @Column(nullable = false)
    private String firsName;
    @Column(nullable = false)

    private String lastName;
    private String patronymic;

    private String post;
    @Column(unique = true)
    private String account;
    private String email;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;


}
