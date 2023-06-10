package com.digital.pm.service.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:service.properties")
@RequiredArgsConstructor
public class RabbitMqConfig {
    @Value("${rabbit.queue}")
    private String queue;
    @Value("${rabbit.routing_key}")
    private String routingKey;
    @Value("${rabbit.exchange}")
    private String exchange;


    private final Logger rabbitLogger;

    @Bean
    public Queue queue() {
        rabbitLogger.info("создание очереди");
        return new Queue(queue, false);
    }

    @Bean
    public DirectExchange directExchange() {
        rabbitLogger.info("создание  exchange");
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        rabbitLogger.info("create Binding ");
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }


}
