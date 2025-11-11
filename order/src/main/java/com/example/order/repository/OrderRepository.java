package com.example.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.order.entity.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Long> {


}
