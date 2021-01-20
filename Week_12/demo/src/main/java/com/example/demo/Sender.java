package com.example.demo;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.Destination;
import javax.jms.Session;

@Component
public class Sender {
    private final JmsTemplate jmsTemplate;

    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostConstruct
    public void sendMessage() {
        this.jmsTemplate.convertAndSend(new ActiveMQQueue("helloworld"), "hello cuicui");
        // jmsTemplate.send("", session -> session.createTextMessage().setText("aaa"));
    }
}
