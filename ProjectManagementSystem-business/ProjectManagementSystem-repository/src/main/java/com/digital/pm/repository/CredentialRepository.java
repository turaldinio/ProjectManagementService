package com.digital.pm.repository;

import com.digital.pm.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential,Long> {
    Optional<Credential> findByLogin(String login);

}
