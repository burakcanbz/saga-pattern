package com.example.order.service;

import com.example.order.dto.CreateOrderDTO;
import com.example.order.entity.Order;
import com.example.order.entity.OrderItem;
import com.example.order.entity.OrderStatus;
import com.example.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(CreateOrderDTO dto) {
        try {
            Order order = new Order();
            order.setUserId(dto.getUserId());
            order.setTotalPrice(dto.getTotalPrice());
            order.setStatus(OrderStatus.PENDING);

            List<OrderItem> items = dto.getItems().stream().map(itemDto -> {
                OrderItem item = new OrderItem();
                item.setProductId(itemDto.getProductId());
                item.setQuantity(itemDto.getQuantity());
                item.setOrder(order);
                return item;
            }).toList();

            order.setItems(new ArrayList<>(items));
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}