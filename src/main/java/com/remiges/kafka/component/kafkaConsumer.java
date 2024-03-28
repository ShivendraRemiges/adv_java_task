package com.remiges.kafka.component;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class kafkaConsumer {

    @KafkaListener(topics = "test_topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Message received: " + message);
    }
}
