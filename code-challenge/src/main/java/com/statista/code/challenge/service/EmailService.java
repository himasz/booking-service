package com.statista.code.challenge.service;

import com.statista.code.challenge.common.error.exception.EmailAuthenticationException;
import com.statista.code.challenge.common.properties.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    public static final String SUCCESSFUL_BOOKING = "Successful Booking";
    JavaMailSenderImpl javaMailSender;
    private final MailProperties mailProperties;

    public EmailService(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
        this.javaMailSender = getJavaMailSender(mailProperties);
    }

    public void sendEmail(String to, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailProperties.getUsername());
            message.setTo(to);
            message.setSubject(SUCCESSFUL_BOOKING);
            message.setText(text);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new EmailAuthenticationException(e.getMessage());
        }
    }

    private static JavaMailSenderImpl getJavaMailSender(MailProperties mailProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
}
