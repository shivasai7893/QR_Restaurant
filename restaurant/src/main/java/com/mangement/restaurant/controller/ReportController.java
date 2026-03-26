package com.mangement.restaurant.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mangement.restaurant.dto.InventoryDTO;
import com.mangement.restaurant.dto.SalesReportDTO;
import com.mangement.restaurant.service.InventoryService;
import com.mangement.restaurant.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;
	private final InventoryService inventoryService;

	public ReportController(ReportService reportService, InventoryService inventoryService) {
		super();
		this.reportService = reportService;
		this.inventoryService = inventoryService;
	}

	// GET → daily sales report
	@GetMapping("/reports/sales")
	public ResponseEntity<SalesReportDTO> getSalesReport(@RequestParam(required = false) String date) {

		LocalDate reportDate = (date != null) ? LocalDate.parse(date) : LocalDate.now(); // default = today

		return ResponseEntity.ok(reportService.getDailySalesReport(reportDate));
	}

	// GET → inventory
	@GetMapping("/inventory")
	public ResponseEntity<InventoryDTO> getInventory() {
		return ResponseEntity.ok(inventoryService.getInventory());
	}
}
