package com.digital.pm.service.amqp;

import com.digital.pm.service.mail.TestMailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageConsume {
    private final TestMailSender mailSender;

    private final MessageConverter messageConverter;
    @Value("${mail.mock:null}")
    private String mailMock;

    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",
            bindings = @QueueBinding(value = @Queue(value = "${notification.queue}", durable = "false"),
                    key = "${notification.routing_key}",
                    exchange = @Exchange(value = "${notification.exchange}", type = ExchangeTypes.DIRECT)
            ))
    public void receiveMessage(Message message) {
        log.info("reading a massage");

        var rabbitMessage = messageConverter.fromMessage(message);


        String mail = mailMock.equals("null") ? message.
                getMessageProperties().
                getHeader("email") :
                mailMock;

        mailSender.sendMail((String) rabbitMessage, mail);

        log.info("the message has been successfully subtracted from the queue");
    }
}
