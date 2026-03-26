package com.mangement.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangement.restaurant.dto.InventoryDTO;
import com.mangement.restaurant.model.MenuItem;
import com.mangement.restaurant.repository.MenuItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final MenuItemRepository menuItemRepository;

	public InventoryService(MenuItemRepository menuItemRepository) {
		super();
		this.menuItemRepository = menuItemRepository;
	}

	// stock threshold
	private static final int LOW_STOCK_LIMIT = 5;

	@Transactional
	public InventoryDTO getInventory() {

		List<MenuItem> allItems = menuItemRepository.findAll();

		List<InventoryDTO.ItemStockDTO> itemStocks = allItems.stream().map(item -> {
			InventoryDTO.ItemStockDTO stock = new InventoryDTO.ItemStockDTO();
			stock.setId(item.getId());
			stock.setName(item.getName());
			stock.setStockQuantity(item.getStockQuantity());
			stock.setPrice(item.getPrice());
			stock.setAvailable(item.getAvailable());
			stock.setCategoryName(item.getSubcategory().getCategory().getName());
			stock.setStockStatus(getStockStatus(item.getStockQuantity()));
			return stock;
		}).toList();

		InventoryDTO inventory = new InventoryDTO();
		inventory.setTotalItems(allItems.size());
		inventory.setAvailableItems((int) allItems.stream().filter(i -> Boolean.TRUE.equals(i.getAvailable())).count());
		inventory.setOutOfStockItems((int) allItems.stream().filter(i -> i.getStockQuantity() == 0).count());
		inventory.setLowStockItems((int) allItems.stream()
				.filter(i -> i.getStockQuantity() > 0 && i.getStockQuantity() <= LOW_STOCK_LIMIT).count());
		inventory.setItemStocks(itemStocks);

		return inventory;
	}

	// update stock quantity
	@Transactional
	public String updateStock(Long itemId, Integer quantity) {
		MenuItem item = menuItemRepository.findById(itemId)
				.orElseThrow(() -> new RuntimeException("Item not found: " + itemId));
		item.setStockQuantity(quantity);

		// auto mark unavailable if stock = 0
		if (quantity == 0) {
			item.setAvailable(false);
		} else {
			item.setAvailable(true);
		}

		menuItemRepository.save(item);
		return "Stock updated for: " + item.getName() + " → " + quantity;
	}

	private String getStockStatus(Integer stock) {
		if (stock == 0)
			return "OUT";
		if (stock <= LOW_STOCK_LIMIT)
			return "LOW";
		return "OK";
	}
}
