package com.digital.pm.service;

import com.digital.pm.service.config.ServiceConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@Import(value = {ServiceConfig.class})
public class TestConfig {
}
