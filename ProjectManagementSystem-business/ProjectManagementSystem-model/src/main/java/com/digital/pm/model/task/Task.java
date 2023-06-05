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
    private Long id;//первичный ключ задачи
    @Column(nullable = false)
    private String name;//название задачи
    private String description;//описание задачи
    @Column(name = "executor_id")
    private Long executorId;//id исполнителя задачи
    @Column(name = "project_id")
    private Long projectId;//id проекта, в котором находится эта задача
    @Column(nullable = false,
            name = "labor_cost")
    private Long laborCost;          //трудозатраты в часах

    @CreationTimestamp
    private Date deadline;      //не может быть меньше чем now +трудозатраты
    @Enumerated(EnumType.STRING)
    private TaskStatus status;//статус в которой находится задача

    @Column(name = "author_id")
    private Long authorId;//id автора задачи
    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;//дата создания задачи
    @CreationTimestamp
    @Column(name = "updated")
    private Date updateTime;//дата обновления задачи

}
