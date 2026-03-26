package com.mangement.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangement.restaurant.constants.OrderStatus;
import com.mangement.restaurant.dto.SalesReportDTO;
import com.mangement.restaurant.model.Order;
import com.mangement.restaurant.model.OrderItem;
import com.mangement.restaurant.repository.OrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final OrderRepository orderRepository;

	public ReportService(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	@Transactional
	public SalesReportDTO getDailySalesReport(LocalDate date) {

		// get start and end of day
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = date.atTime(23, 59, 59);

		// get all DONE/DELIVERED orders for the day
		List<Order> orders = orderRepository.findByCreatedAtBetweenAndStatusIn(startOfDay, endOfDay,
				List.of(OrderStatus.DONE, OrderStatus.DELIVERED));

		// total revenue
		double totalRevenue = orders.stream().mapToDouble(Order::getTotalPrice).sum();

		// all order items flat list
		List<OrderItem> allItems = orders.stream().flatMap(o -> o.getOrderItems().stream()).toList();

		// total items sold
		int totalItemsSold = allItems.stream().mapToInt(OrderItem::getQuantity).sum();

		// item wise sales grouping
		Map<String, SalesReportDTO.ItemSalesDTO> itemSalesMap = new LinkedHashMap<>();

		for (OrderItem item : allItems) {
			String name = item.getMenuItem().getName();
			itemSalesMap.computeIfAbsent(name, k -> {
				SalesReportDTO.ItemSalesDTO dto = new SalesReportDTO.ItemSalesDTO();
				dto.setItemName(k);
				dto.setQuantitySold(0);
				dto.setRevenue(0.0);
				return dto;
			});
			SalesReportDTO.ItemSalesDTO dto = itemSalesMap.get(name);
			dto.setQuantitySold(dto.getQuantitySold() + item.getQuantity());
			dto.setRevenue(dto.getRevenue() + item.getPrice());
		}

		// best selling item
		String bestSelling = itemSalesMap.values().stream()
				.max(Comparator.comparingInt(SalesReportDTO.ItemSalesDTO::getQuantitySold))
				.map(SalesReportDTO.ItemSalesDTO::getItemName).orElse("No sales yet");

		SalesReportDTO report = new SalesReportDTO();
		report.setDate(date);
		report.setTotalOrders(orders.size());
		report.setTotalRevenue(totalRevenue);
		report.setTotalItemsSold(totalItemsSold);
		report.setBestSellingItem(bestSelling);
		report.setItemWiseSales(new ArrayList<>(itemSalesMap.values()));

		return report;
	}
}
