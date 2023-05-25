package com.digital.pm.repository;

import com.digital.pm.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long>, JpaSpecificationExecutor<Project> {
boolean existsByProjectCode(String code);
}
