package com.mangement.restaurant.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangement.restaurant.dto.MenuItemRequestDTO;
import com.mangement.restaurant.dto.MenuItemResponseDTO;
import com.mangement.restaurant.model.MenuItem;
import com.mangement.restaurant.model.Subcategory;
import com.mangement.restaurant.repository.MenuItemRepository;
import com.mangement.restaurant.repository.SubcategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final MenuItemRepository menuItemRepository;
	private final SubcategoryRepository subcategoryRepository;

	public AdminService(MenuItemRepository menuItemRepository, SubcategoryRepository subcategoryRepository) {
		super();
		this.menuItemRepository = menuItemRepository;
		this.subcategoryRepository = subcategoryRepository;
	}

	// ─────────────────────────────────────────
	// POST → Add new menu item
	// ─────────────────────────────────────────
	@Transactional
	public MenuItemResponseDTO addMenuItem(MenuItemRequestDTO request) {

		Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId())
				.orElseThrow(() -> new RuntimeException("Subcategory not found: " + request.getSubcategoryId()));

		MenuItem item = new MenuItem();
		item.setName(request.getName());
		item.setDescription(request.getDescription());
		item.setPrice(request.getPrice());
		item.setSubcategory(subcategory);
		item.setAvailable(request.getAvailable());
		item.setStockQuantity(request.getStockQuantity());

		MenuItem saved = menuItemRepository.save(item);
		return mapToDTO(saved);
	}

	// ─────────────────────────────────────────
	// PUT → Update price of menu item
	// ─────────────────────────────────────────
	@Transactional
	public MenuItemResponseDTO updateMenuItem(Long id, MenuItemRequestDTO request) {

		MenuItem item = menuItemRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Menu item not found: " + id));

		// update only fields that are sent
		if (request.getPrice() != null)
			item.setPrice(request.getPrice());
		if (request.getName() != null)
			item.setName(request.getName());
		if (request.getDescription() != null)
			item.setDescription(request.getDescription());
		if (request.getAvailable() != null)
			item.setAvailable(request.getAvailable());
		if (request.getStockQuantity() != null)
			item.setStockQuantity(request.getStockQuantity());

		return mapToDTO(menuItemRepository.save(item));
	}

	// ─────────────────────────────────────────
	// DELETE → Remove menu item
	// ─────────────────────────────────────────
	@Transactional
	public String deleteMenuItem(Long id) {
		menuItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu item not found: " + id));
		menuItemRepository.deleteById(id);
		return "Menu item deleted successfully: " + id;
	}

	// ─────────────────────────────────────────
	// GET → All menu items
	// ─────────────────────────────────────────
	@Transactional
	public List<MenuItemResponseDTO> getAllMenuItems() {
		return menuItemRepository.findAll().stream().map(this::mapToDTO).toList();
	}

	// ─────────────────────────────────────────
	// Map Entity → DTO
	// ─────────────────────────────────────────
	public MenuItemResponseDTO mapToDTO(MenuItem item) {
		MenuItemResponseDTO dto = new MenuItemResponseDTO();
		dto.setId(item.getId());
		dto.setName(item.getName());
		dto.setDescription(item.getDescription());
		dto.setPrice(item.getPrice());
		dto.setAvailable(item.getAvailable());
		dto.setStockQuantity(item.getStockQuantity());
		dto.setSubcategoryName(item.getSubcategory().getName());
		dto.setCategoryName(item.getSubcategory().getCategory().getName());
		return dto;
	}
}
