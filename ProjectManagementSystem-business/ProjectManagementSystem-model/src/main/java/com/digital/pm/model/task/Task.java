package com.digital.pm.model.task;

import com.digital.pm.common.enums.TaskStatus;
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
    @Column(name = "executor_id")
    private Long executorId;
    @Column(name = "project_id")
    private Long projectId;
    @Column(nullable = false,
            name = "labor_cost")
    private int laborCost;          //трудозатраты в часах

    @CreationTimestamp
    private Date deadline;      //не может быть меньше чем now +трудозатраты
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "author_id")
    private Long authorId;
    @CreationTimestamp
    @Column(name = "creation_date")
    private Date dateOfCreation;
    @CreationTimestamp
    @Column(name = "updated")
    private Date updateTime;

}
