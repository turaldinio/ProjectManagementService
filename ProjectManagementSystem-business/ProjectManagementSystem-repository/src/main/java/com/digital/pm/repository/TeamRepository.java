package com.digital.pm.repository;

import com.digital.pm.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {

    void removeTeamByEmployeeIdAndProjectId(Long employeeId, Long projectId);


    List<Team> findAllByProjectId(Long projectId);

    Optional<Team> findByEmployeeIdAndProjectId(Long employeeId, Long projectId);


    Boolean existsByEmployeeIdAndProjectId(Long employeeId, Long projectId);
}
