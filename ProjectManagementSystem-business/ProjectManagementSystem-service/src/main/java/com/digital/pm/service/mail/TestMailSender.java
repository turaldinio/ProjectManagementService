package com.digital.pm.service.mail;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestMailSender {
    private final JavaMailSender javaMailSender;
    private final Logger emailLogger;

    public void sendMail(String text, String email) {
        emailLogger.info("creating a mail object");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("turalguluev@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("New task");
        simpleMailMessage.setText(text);

        emailLogger.info("sending a message");

        javaMailSender.send(simpleMailMessage);

    }
}
