package com.mangement.restaurant.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mangement.restaurant.model.MenuItem;
import com.mangement.restaurant.model.Subcategory;
import com.mangement.restaurant.repository.MenuItemRepository;
import com.mangement.restaurant.repository.SubcategoryRepository;

@Service
public class MenuItemService {

	public final MenuItemRepository menuItemRepository;
	public final SubcategoryRepository subcategoryRepository;

	@Autowired
	public MenuItemService(MenuItemRepository menuItemRepository,SubcategoryRepository subcategoryRepository) {
		this.menuItemRepository = menuItemRepository;
		this.subcategoryRepository = subcategoryRepository;
	}

	// *************************Create and Update*************************

	public MenuItem saveMenuItem(MenuItem menuItem) {

		Subcategory subcategory = subcategoryRepository.findById(menuItem.getSubcategory().getId())
				.orElseThrow(() -> new RuntimeException("SubCategory not found"));

		menuItem.setSubcategory(subcategory);
		return menuItemRepository.save(menuItem);
	}

	public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
		return menuItemRepository.findById(id).map(existingMenuItem -> {

			// Update only if the field is provided in the request
			if (menuItem.getName() != null) {
				existingMenuItem.setName(menuItem.getName());
			}
			if (menuItem.getPrice() != null) {
				existingMenuItem.setPrice(menuItem.getPrice());
			}
			if (menuItem.getDescription() != null) {
				existingMenuItem.setDescription(menuItem.getDescription());
			}

			return menuItemRepository.save(existingMenuItem);

		}).orElseThrow(() -> new RuntimeException("MenuItem not found with ID: " + id));
	}

	// ************************* Read *************************
	public List<MenuItem> getMenuItemByName(String name) {
		return menuItemRepository.findByName(name);
	}

	public List<MenuItem> getAllMenuItem() {
		return menuItemRepository.findAll();
	}

	public Optional<MenuItem> getMenuItemById(Long id) {
		return menuItemRepository.findById(id);
	}

	// ************************* delete *************************

	public void deleteMenuItemById(Long id) {
		menuItemRepository.deleteById(id);
	}

	public void deleteMenuItem(MenuItem menuItem) {
		menuItemRepository.delete(menuItem);
	}

	public void deleteAllMenuItem() {
		menuItemRepository.deleteAll();
	}

}
