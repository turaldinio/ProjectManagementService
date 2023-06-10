package com.digital.pm.service.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final Logger rabbitLogger;

    @Bean
    public ConnectionFactory connectionFactory() {
        rabbitLogger.info("creating connection factory");
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("admin");
        cachingConnectionFactory.setPassword("admin");
        cachingConnectionFactory.setVirtualHost("/");
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        rabbitLogger.info("админка");

        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        rabbitLogger.info("teamplate");

        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue queue() {
        rabbitLogger.info("создание очереди");
        return new Queue("school-queue", false);
    }

    @Bean
    public DirectExchange directExchange() {
        rabbitLogger.info("создание точки устанвоки exchane");
        return new DirectExchange("school-exchange");
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        rabbitLogger.info("Биндинг очередь ");
        return BindingBuilder.bind(queue).to(directExchange).with("school-key");
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer1() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(queue());
        //тут ловим сообщения из queue1
        container.setMessageListener(message -> rabbitLogger.info("received from queue1 : " + new String(message.getBody())));
        return container;
    }
}
