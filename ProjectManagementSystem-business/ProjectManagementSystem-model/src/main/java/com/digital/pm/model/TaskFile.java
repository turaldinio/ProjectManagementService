package com.digital.pm.model;

import jakarta.persistence.*;

@Entity
@Table(name = "task_file")
public class TaskFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_path")
    private String path;
}
