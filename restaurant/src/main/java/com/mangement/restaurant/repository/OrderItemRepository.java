package com.mangement.restaurant.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mangement.restaurant.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
