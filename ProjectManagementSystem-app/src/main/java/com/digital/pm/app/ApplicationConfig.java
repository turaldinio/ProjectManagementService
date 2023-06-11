package com.digital.pm.app;

import com.digital.pm.web.controller.ControllerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(value = {ControllerConfig.class})
public class ApplicationConfig {

}
