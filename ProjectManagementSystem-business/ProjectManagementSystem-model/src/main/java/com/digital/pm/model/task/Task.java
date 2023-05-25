package com.digital.pm.model.task;

import com.digital.pm.common.enums.TaskStatus;
import com.digital.pm.model.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToOne

    private Employee executor;
    @Column(nullable = false,
            name = "labor_cost")
    private int laborCosts;

    @CreationTimestamp
    private Date deadline;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String author;
    @CreationTimestamp
    @Column(name = "creation_date")
    private Date dateOfCreation;
    @CreationTimestamp
    @Column(name = "updated")
    private Date updateTime;

}
