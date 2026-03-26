package com.mangement.restaurant.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mangement.restaurant.admin.service.AdminService;
import com.mangement.restaurant.dto.MenuItemRequestDTO;
import com.mangement.restaurant.dto.MenuItemResponseDTO;
import com.mangement.restaurant.model.Category;
import com.mangement.restaurant.model.MenuItem;
import com.mangement.restaurant.service.CategoryService;
import com.mangement.restaurant.service.InventoryService;
import com.mangement.restaurant.service.MenuItemService;
import com.mangement.restaurant.service.SubcategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/menu-items")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	private final InventoryService inventoryService;
	private final CategoryService categoryService;
	private final MenuItemService menuItemService;

	@Autowired
	public AdminController(AdminService adminService, InventoryService inventoryService,
			CategoryService categoryService, MenuItemService menuItemService) {
		super();
		this.adminService = adminService;
		this.inventoryService = inventoryService;
		this.categoryService = categoryService;
		this.menuItemService = menuItemService;
	}

	// *******************category start here for admin ********************
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/savecategory")
	public Category saveCategory(@RequestBody Category catogery) {
		return categoryService.saveCategory(catogery);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/updatecategory")
	public Category updateCategory(@RequestParam(name = "categoryid") Long categoryid, @RequestBody Category catogery) {
		return categoryService.updateCategory(categoryid, catogery);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER','ROLE_ADMIN','ROLE_WAITER','ROLE_KITCHEN')")
	@GetMapping("/allcategory")
	public List<Category> getAllCategory() {
		return categoryService.getAllCategory();
	}

	// delete
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("deletecategorybyid")
	public void deletecategoryById(@RequestParam(name = "categoryid") Long categoryid) {
		categoryService.deleteCategoryById(categoryid);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("deletecategory")
	public void deletecategoryById(Category category) {
		categoryService.deleteCategory(category);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("deleteallcategory")
	public void deleteAllcategory() {
		categoryService.deleteAllCategory();
	}

	// *******************category end here for admin ********************

	// ====================================================================
	// Menu item controller for admin start
	// ====================================================================

	// POST → add item
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<MenuItemResponseDTO> addItem(@RequestBody MenuItemRequestDTO request) {
		return ResponseEntity.ok(adminService.addMenuItem(request));
	}

	// PUT → update item / price
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<MenuItemResponseDTO> updateItem(@PathVariable Long id,
			@RequestBody MenuItemRequestDTO request) {
		return ResponseEntity.ok(adminService.updateMenuItem(id, request));
	}

	// DELETE → remove item
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteItem(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.deleteMenuItem(id));
	}

	// delete
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("deletemenuitembyid")
	public void deleteMenuItem(@RequestParam(name = "menuItemid") Long menuItemid) {
		menuItemService.deleteMenuItemById(menuItemid);
	}

	@DeleteMapping("deletemenuitem")
	public void deleteMenuItemById(MenuItem menuItem) {
		menuItemService.deleteMenuItem(menuItem);
	}

	@DeleteMapping("deleteallmenuitem")
	public void deleteAllMenuItem() {
		menuItemService.deleteAllMenuItem();
	}

	// GET → all items
	@GetMapping
	public ResponseEntity<List<MenuItemResponseDTO>> getAllItems() {
		return ResponseEntity.ok(adminService.getAllMenuItems());
	}

	// PUT → update stock
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/{id}/stock")
	public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
		return ResponseEntity.ok(inventoryService.updateStock(id, quantity));
	}

	// ====================================================================
	// Menu item controller for admin end
	// ====================================================================

}
