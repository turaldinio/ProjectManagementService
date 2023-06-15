package com.digital.pm.repository;

import com.digital.pm.model.DependentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependentTaskRepository extends JpaRepository<DependentTask,Long> {
}
