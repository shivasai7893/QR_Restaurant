package com.mangement.restaurant.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.mangement.restaurant.constants.OrderPriority;
import com.mangement.restaurant.constants.OrderStatus;
import com.mangement.restaurant.model.Order;
import com.mangement.restaurant.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class KitchenQueueService {

    private final OrderRepository orderRepository;
    
    public KitchenQueueService(OrderRepository orderRepository)
    {
    	this.orderRepository=orderRepository;
    }

    // Priority Queue
    // Higher priority value = comes first
    // If same priority → earlier createdAt comes first (FCFS)
    private final PriorityQueue<Order> kitchenQueue = new PriorityQueue<>(
        Comparator
            .comparingInt(Order::getPriority).reversed()   // HIGH first
            .thenComparing(Order::getCreatedAt)            // FCFS for same priority
    );

    // Load pending orders into queue on app startup
    @jakarta.annotation.PostConstruct
    public void loadQueueOnStartup() {
        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING);
        kitchenQueue.addAll(pendingOrders);
        System.out.println("Kitchen Queue loaded with " + pendingOrders.size() + " orders");
    }

    // ─────────────────────────────────────────
    // Add order to queue when placed
    // ─────────────────────────────────────────
    public void addToQueue(Order order) {
        int priority = calculatePriority(order);
        order.setPriority(priority);
        orderRepository.save(order);
        kitchenQueue.offer(order);
    }

    // ─────────────────────────────────────────
    // Priority Logic
    // VIP table        → HIGH  (2)
    // Large order >500 → MEDIUM (1)
    // Normal           → LOW   (0)
    // ─────────────────────────────────────────
    private int calculatePriority(Order order) {
        if (Boolean.TRUE.equals(order.getIsVip())) {
            return OrderPriority.HIGH.getValue();     // 2
        } else if (order.getTotalPrice() > 500) {
            return OrderPriority.MEDIUM.getValue();   // 1
        } else {
            return OrderPriority.LOW.getValue();       // 0
        }
    }

    // ─────────────────────────────────────────
    // GET kitchen queue — sorted list
    // ─────────────────────────────────────────
    public List<Order> getKitchenQueue() {
        // return sorted copy — don't expose internal queue directly
        List<Order> sorted = new ArrayList<>(kitchenQueue);
        sorted.sort(
            Comparator
                .comparingInt(Order::getPriority).reversed()
                .thenComparing(Order::getCreatedAt)
        );
        return sorted;
    }

    // ─────────────────────────────────────────
    // GET next order to prepare (peek top)
    // ─────────────────────────────────────────
    public Order getNextOrder() {
        Order next = kitchenQueue.peek();
        if (next == null) {
            throw new RuntimeException("Queue is empty — no orders pending");
        }
        return next;
    }

    // ─────────────────────────────────────────
    // PUT mark order as DONE by kitchen
    // ─────────────────────────────────────────
    public Order markAsDone(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        // remove from queue
        kitchenQueue.removeIf(o -> o.getId().equals(orderId));

        // update status
        order.setStatus(OrderStatus.DONE);
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // ─────────────────────────────────────────
    // GET queue size
    // ─────────────────────────────────────────
    public int getQueueSize() {
        return kitchenQueue.size();
    }

    // ─────────────────────────────────────────
    // GET orders by priority label
    // ─────────────────────────────────────────
    public List<Order> getOrdersByPriority(int priority) {
        return kitchenQueue.stream()
                .filter(o -> o.getPriority() == priority)
                .sorted(Comparator.comparing(Order::getCreatedAt))
                .toList();
    }
}
