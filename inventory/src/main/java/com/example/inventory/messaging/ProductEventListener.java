package com.example.inventory.listener;

import com.example.inventory.dto.ProductCreatedEventDTO;
import com.example.inventory.entity.Inventory;
import com.example.inventory.repository.InventoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductEventListener {

    @Autowired
    private InventoryRepository inventoryRepository;

    @RabbitListener(queues = "product.created.queue")
    public void handleProductCreated(ProductCreatedEventDTO event) {
        System.out.println("✓ Product created event received: " + event.getProductId());

        Inventory inventory = new Inventory();
        inventory.setProductId(event.getProductId());
        inventory.setCategory(event.getCategory());
        inventory.setQuantity(event.getQuantity());

        inventoryRepository.save(inventory);

        System.out.println("✓ Inventory created for product: " + event.getProductId());
    }
}