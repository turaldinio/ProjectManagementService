package com.digital.pm.service;

import com.digital.pm.service.mail.TestMailSender;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication()
@ComponentScan("com.digital")
public class TestConfig {
}
