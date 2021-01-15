package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    private final String DEFAULT_TOPIC = "quickstart-events";

    @KafkaListener(topics = DEFAULT_TOPIC)
    public void processMessage(String content) {
        System.out.println(String.format("thread %s receive %s", Thread.currentThread(), content));
    }
}
