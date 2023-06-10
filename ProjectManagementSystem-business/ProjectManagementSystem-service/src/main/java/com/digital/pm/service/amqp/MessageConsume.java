package com.digital.pm.service.amqp;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class MessageConsume {
    private final RabbitTemplate rabbitTemplate;
    private final Logger consumeLogger;

    @RabbitListener(queues = "${rabbit.queue}")
    public void receiveMessage(Message message) {
        consumeLogger.info("reading a massage");
        System.out.println("you have new message " + new String(message.getBody()));
    }
}
