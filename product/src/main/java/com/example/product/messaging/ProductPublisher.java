package com.example.product.messaging;

import com.example.product.dto.ProductCreatedEventDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    public ProductPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishProductCreated(ProductCreatedEventDTO event) {
        rabbitTemplate.convertAndSend(
                "product-exchange",
                "product.create",
                event
        );
        System.out.println("✓ Product created event published: " + event.getProductId());
    }
}