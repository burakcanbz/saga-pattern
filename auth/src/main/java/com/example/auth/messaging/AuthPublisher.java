package com.example.auth.messaging;

import com.example.auth.dto.AuthRegisteredEventDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthPublisher {

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    public AuthPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserRegistered(AuthRegisteredEventDTO event) {
        rabbitTemplate.convertAndSend(
                "auth-exchange",
                "auth.registered",
                event
        );
        System.out.println("✓ auth registered event published: " + event.getUsername());
    }
}