package com.digital.pm.model;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.model.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToOne

    private Employee executor;
    @Column(nullable = false)
    private int laborCosts;

    @CreationTimestamp
    private Date deadline;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String author;
    @CreationTimestamp
    private Date dateOfCreation;
    @CreationTimestamp
    private Date updateTime;

}
