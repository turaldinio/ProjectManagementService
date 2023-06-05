package com.digital.pm.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.digital.pm.repository")
@EntityScan(basePackages = "com.digital.pm.model")
public class RepositoryConfiguration {
}
