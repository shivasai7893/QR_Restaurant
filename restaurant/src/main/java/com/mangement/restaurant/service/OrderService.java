package com.mangement.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.mangement.restaurant.constants.OrderStatus;
import com.mangement.restaurant.dto.BillDTO;
import com.mangement.restaurant.dto.OrderRequestDTO;
import com.mangement.restaurant.model.MenuItem;
import com.mangement.restaurant.model.Order;
import com.mangement.restaurant.model.OrderItem;
import com.mangement.restaurant.repository.MenuItemRepository;
import com.mangement.restaurant.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final KitchenQueueService kitchenQueueService;
    
    public OrderService(OrderRepository orderRepository, MenuItemRepository menuItemRepository,KitchenQueueService kitchenQueueService) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.kitchenQueueService=kitchenQueueService;
    }

    // POST - Place Order
    public Order placeOrder(OrderRequestDTO request) {

        Order order = new Order();
        order.setTableNumber(request.getTableNumber());
        order.setIsVip(request.getIsVip());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> items = new ArrayList<>();
        double total = 0;

        for (var itemReq : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found: " + itemReq.getMenuItemId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(menuItem.getPrice().doubleValue() * itemReq.getQuantity());

            total += orderItem.getPrice();
            items.add(orderItem);
        }

        order.setOrderItems(items);
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);
        // ← ADD THIS LINE → puts order in kitchen queue
        kitchenQueueService.addToQueue(savedOrder);

        return savedOrder;
    }

    // GET - Get Order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    // GET - Get All Orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // PUT - Update Order Status
    public Order updateStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // GET - Get Orders by Status
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
    
    // GET /orders/active ← all live orders (PENDING or PREPARING)
    public List<Order> getActiveOrders() {
        List<Order> pending = orderRepository.findByStatus(OrderStatus.PENDING);
        List<Order> preparing = orderRepository.findByStatus(OrderStatus.PREPARING);
        List<Order> combined = new ArrayList<>();
        combined.addAll(pending);
        combined.addAll(preparing);
        return combined;
    }

    // GET /orders/table/{id} ← orders by table number
    public List<Order> getOrdersByTable(int tableNumber) {
        return orderRepository.findByTableNumber(tableNumber);
    }

    // GET /billing/{orderId} ← generate bill
    public BillDTO generateBill(Long orderId) {
        Order order = getOrderById(orderId);

        BillDTO bill = new BillDTO();
        bill.setOrderId(order.getId());
        bill.setTableNumber(order.getTableNumber());
        bill.setStatus(order.getStatus().name());
        bill.setTotalPrice(order.getTotalPrice());

        List<BillDTO.BillItemDTO> billItems = new ArrayList<>();
        for (OrderItem oi : order.getOrderItems()) {
            BillDTO.BillItemDTO billItem = new BillDTO.BillItemDTO();
            billItem.setItemName(oi.getMenuItem().getName());
            billItem.setQuantity(oi.getQuantity());
            billItem.setUnitPrice(oi.getMenuItem().getPrice().doubleValue());
            billItem.setSubtotal(oi.getPrice());
            billItems.add(billItem);
        }

        bill.setItems(billItems);
        return bill;
    }
}