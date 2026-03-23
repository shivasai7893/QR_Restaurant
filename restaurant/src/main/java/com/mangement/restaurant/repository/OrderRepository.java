package com.mangement.restaurant.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mangement.restaurant.constants.OrderStatus;
import com.mangement.restaurant.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByTableNumber(Integer tableNumber);
}
