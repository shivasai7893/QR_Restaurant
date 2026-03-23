package com.mangement.restaurant.dto;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mangement.restaurant.constants.OrderStatus;
import com.mangement.restaurant.model.Order;

import java.util.List;

public interface OrderResponseDTO extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByTableNumber(Integer tableNumber);
}
