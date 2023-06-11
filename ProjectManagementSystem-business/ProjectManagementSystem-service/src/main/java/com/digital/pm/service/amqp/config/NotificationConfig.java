package com.digital.pm.service.amqp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Data
@Slf4j
public class NotificationConfig {
    @Value("${notification.routing_key}")
    private String routingKey;
    @Value("${notification.exchange}")
    private String exchange;


    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }


}
