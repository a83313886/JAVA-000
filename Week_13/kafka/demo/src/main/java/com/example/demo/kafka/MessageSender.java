package com.example.demo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Component
public class MessageSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String DEFAULT_TOPIC = "quickstart-events";

    public MessageSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send() {
        ListenableFuture<SendResult<String, String>> listenableFuture = this.kafkaTemplate.send(DEFAULT_TOPIC, "hello");
        listenableFuture.addCallback(System.out::println, System.out::println);
    }

    public void send1Multiple(int count) {
        IntStream.range(0, count).forEach(i -> {
            this.kafkaTemplate.send(DEFAULT_TOPIC, "keysA" + ThreadLocalRandom.current().nextInt(), "quickstart-events" + i);
            // 暂停一下，模拟消息逐渐产生
            try {
                Thread.sleep(Math.abs(ThreadLocalRandom.current().nextLong(0, 1000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @PostConstruct
    public void doSend() {
        // send();
        // 发送弄成异步执行，免得阻塞应用启动导致listener无法生效
        CompletableFuture.runAsync(() -> send1Multiple(1000));
    }
}
