package com.digital.pm.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestMailSender {
    private final JavaMailSender javaMailSender;

    public void sendMail(String text, String email) {
        log.info("creating a mail object");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("turalguluev@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("New task");
        simpleMailMessage.setText(text);

        log.info(String.format("the message was sent to the %s mail", email));


        javaMailSender.send(simpleMailMessage);

    }
}
