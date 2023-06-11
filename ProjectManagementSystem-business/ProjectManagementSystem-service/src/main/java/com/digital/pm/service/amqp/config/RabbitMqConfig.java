package com.digital.pm.service.amqp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class RabbitMqConfig {
    @Value("${notification.queue}")
    private String queue;
    @Value("${notification.routing_key}")
    private String routingKey;
    @Value("${notification.exchange}")
    private String exchange;


    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }


}
