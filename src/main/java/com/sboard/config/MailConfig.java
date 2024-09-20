package com.sboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

@Bean
public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(465); // SSL 포트

    mailSender.setUsername("wlgus5946@gmail.com");
    mailSender.setPassword("dqys begj jhxb ffhs");

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    //props.put("mail.smtp.starttls.enable", "false");
    props.put("mail.debug", "false");
    props.put("mail.smtp.ssl.enable", "true");

    return mailSender;
}
}