package com.digital.pm.model.project;

import com.digital.pm.common.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//первичный ключ проекта
    @Column(nullable = false,unique = true,name = "project_code")
    private String projectCode;//уникальный код проекта
    @Column(nullable = false)
    private String name;//название проекта
    private String description;//описание проекта
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;//статус, в котором находится проект

}
