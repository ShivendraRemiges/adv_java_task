package com.remiges.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remiges.kafka.component.KafkaProducer;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer producer;

    @GetMapping("/send-message")
    public String sendMessage() {
        producer.sendMessage("Kafka is running properly and msg is received!");
        return "Message sent to Kafka";
    }
}
