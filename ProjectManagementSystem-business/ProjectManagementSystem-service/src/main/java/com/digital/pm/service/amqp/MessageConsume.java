package com.digital.pm.service.amqp;

import com.digital.pm.service.mail.TestMailSender;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageConsume {
    private final TestMailSender mailSender;

    private final Gson gson;
    @Value("${mail.mock:null}")
    private String mailMock;

    @RabbitListener(queues = "${rabbit.queue}")

    public void receiveMessage(Message message) {
        log.info("reading a massage");

        var rabbitMessage = gson.fromJson(new String(message.getBody()), RabbitMessage.class);

        String mail = mailMock.equals("null") ? message.
                getMessageProperties().
                getHeader("email") :
                mailMock;

        mailSender.sendMail(rabbitMessage.getBody(), mail);

        log.info(String.format("the message was sent to the %s mail", mail));
    }
}
