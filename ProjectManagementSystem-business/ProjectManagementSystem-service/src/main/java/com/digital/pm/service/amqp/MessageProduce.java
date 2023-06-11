package com.digital.pm.service.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProduce {
    @Value("${rabbit.exchange}")
    private String exchange;
    @Value("${rabbit.routing_key}")
    private String routingKey;

    private final MessageConverter messageConverter;
    private final RabbitTemplate rabbitTemplate;

    public void notifyAnEmployee(Long taskId, String employeeEmail) {
        log.info("sending a massage to the rabbitmq server");


        var messageProperties = new MessageProperties();
        messageProperties.setHeader("email", employeeEmail);

        var rabbitMessage = messageConverter.toMessage(String.format("you have been assigned task with %d id", taskId), messageProperties);

        rabbitTemplate.convertAndSend(exchange, routingKey, rabbitMessage);


    }
}
