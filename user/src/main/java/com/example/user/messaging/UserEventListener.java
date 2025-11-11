package com.example.user.messaging;

import com.example.user.dto.AuthRegisteredEventDTO;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEventListener {

    @Autowired
    private UserRepository userRepository;

    @RabbitListener(queues = "auth.registered.queue")
    public void handleUserCreated(AuthRegisteredEventDTO event) {
        System.out.println("✓ Auth registered event received: " + event.getUsername());

        User user = new User();
        user.setUsername(event.getUsername());
        user.setEmail(event.getEmail());
        user.setPassword(event.getHashedPassword());

        userRepository.save(user);

        System.out.println("✓ User created" + event.getUsername());
    }
}
