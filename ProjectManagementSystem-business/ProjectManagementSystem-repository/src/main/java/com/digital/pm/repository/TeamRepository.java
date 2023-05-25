package com.digital.pm.repository;

import com.digital.pm.model.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {

    Team deleteByEmployeeIdAndProjectId(Long employeeId, Long projectId);

    boolean existsByEmployeeIdAndProjectId(Long employeeId, Long projectId);

    List<Team> findAllByProjectId(Long projectId);





}
