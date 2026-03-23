package com.mangement.restaurant.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mangement.restaurant.constants.OrderStatus;
import com.mangement.restaurant.dto.BillDTO;
import com.mangement.restaurant.dto.OrderRequestDTO;
import com.mangement.restaurant.model.Order;
import com.mangement.restaurant.repository.MenuItemRepository;
import com.mangement.restaurant.repository.OrderRepository;
import com.mangement.restaurant.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Place Order
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequestDTO request) {
        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    // Get Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // Get All Orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Update Status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }

    // Get by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }
    
 // ─── Step 3: Waiter Dashboard 
    
    // GET /orders/active
    @GetMapping("/active")
    public ResponseEntity<List<Order>> getActiveOrders() {
        return ResponseEntity.ok(orderService.getActiveOrders());
    }

    // GET /orders/table/{tableNumber}
    @GetMapping("/table/{tableNumber}")
    public ResponseEntity<List<Order>> getOrdersByTable(@PathVariable int tableNumber) {
        return ResponseEntity.ok(orderService.getOrdersByTable(tableNumber));
    }

    // PUT /orders/{id}/status
    @PutMapping("/waiter/{id}/status")
    public ResponseEntity<Order> WaiterDashboardUpdateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }

    // GET /billing/{orderId}
    @GetMapping("/billing/{orderId}")
    public ResponseEntity<BillDTO> generateBill(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.generateBill(orderId));
    }
}
