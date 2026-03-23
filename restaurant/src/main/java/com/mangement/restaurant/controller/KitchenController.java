package com.mangement.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mangement.restaurant.model.Order;
import com.mangement.restaurant.service.KitchenQueueService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kitchen")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenQueueService kitchenQueueService;
    
    public KitchenController(KitchenQueueService kitchenQueueService)
    {
    	this.kitchenQueueService=kitchenQueueService;
    }

    // GET full kitchen queue (sorted by priority + FCFS)
    @GetMapping("/queue")
    public ResponseEntity<List<Order>> getQueue() {
        return ResponseEntity.ok(kitchenQueueService.getKitchenQueue());
    }

    // GET next order to prepare
    @GetMapping("/queue/next")
    public ResponseEntity<Order> getNextOrder() {
        return ResponseEntity.ok(kitchenQueueService.getNextOrder());
    }

    // PUT mark order as done
    @PutMapping("/done/{orderId}")
    public ResponseEntity<Order> markDone(@PathVariable Long orderId) {
        return ResponseEntity.ok(kitchenQueueService.markAsDone(orderId));
    }

    // GET queue size
    @GetMapping("/queue/size")
    public ResponseEntity<Map<String, Integer>> getQueueSize() {
        return ResponseEntity.ok(Map.of("pendingOrders", kitchenQueueService.getQueueSize()));
    }

    // GET HIGH priority orders only
    @GetMapping("/queue/vip")
    public ResponseEntity<List<Order>> getVipOrders() {
        return ResponseEntity.ok(kitchenQueueService.getOrdersByPriority(2));
    }

    // GET NORMAL priority orders only
    @GetMapping("/queue/normal")
    public ResponseEntity<List<Order>> getNormalOrders() {
        return ResponseEntity.ok(kitchenQueueService.getOrdersByPriority(0));
    }
}
