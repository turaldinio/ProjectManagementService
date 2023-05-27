package com.digital.pm.model.employee;

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
    @Column(nullable = false,
            name = "first_name")
    private String firstName;
    @Column(nullable = false,
            name = "last_name")
    private String lastName;
    @Column(unique = true)
    private String account;
    private String patronymic;
    private String password;
    private String post;

    private String email;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;


}
