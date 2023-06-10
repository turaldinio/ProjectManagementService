package com.digital.pm.service.amqp;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProduce {
    @Value("${rabbit.exchange}")
    private String exchange;
    @Value("${rabbit.routing_key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final Logger produceLogger;

    public void notifyAnEmployee(Long taskId, Long employeeId) {
        produceLogger.info("sending a message");
        rabbitTemplate.convertAndSend(exchange, routingKey,
                String.format("task %d is assigned to employee %d", taskId, employeeId)
        );

    }
}
