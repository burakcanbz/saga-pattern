package com.example.product.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Exchange
    @Bean
    public DirectExchange productExchange() {
        return new DirectExchange("product-exchange", true, false);
    }

    // Queue
    @Bean
    public Queue productCreatedQueue() {
        return new Queue("product.created.queue", true);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Binding
    @Bean
    public Binding productBinding(Queue productCreatedQueue, DirectExchange productExchange) {
        return BindingBuilder.bind(productCreatedQueue)
                .to(productExchange)
                .with("product.create");
    }
}