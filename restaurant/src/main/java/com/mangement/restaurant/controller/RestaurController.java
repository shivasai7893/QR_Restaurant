package com.mangement.restaurant.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mangement.restaurant.model.Category;
import com.mangement.restaurant.model.MenuItem;
import com.mangement.restaurant.model.Subcategory;
import com.mangement.restaurant.service.CategoryService;
import com.mangement.restaurant.service.MenuItemService;
import com.mangement.restaurant.service.SubcategoryService;

@RestController
@RequestMapping("/menu")
public class RestaurController {
	
	private final SubcategoryService subcategoryService;
	
	private final CategoryService categoryService;
	
	private final MenuItemService menuItemService;
	
	@Autowired
	public RestaurController(SubcategoryService subcategoryService,CategoryService categoryService,MenuItemService menuItemService)
	{
		this.subcategoryService=subcategoryService;
		this.categoryService=categoryService;
		this.menuItemService=menuItemService;
	}
	
	//************************  Operation's on Category  ***********************************
	//create or update
	@PutMapping("/savecategory")
	public Category saveCategory(@RequestBody Category catogery)
	{
		return categoryService.saveCategory(catogery);
	}
	
	@PutMapping("/updatecategory")
	public Category updateCategory(@RequestParam(name="categoryid")Long categoryid,@RequestBody Category catogery)
	{
		return categoryService.updateCategory(categoryid,catogery);
	}
	
	
	//read
	@GetMapping("/categorybyname")
	public List<Category> getCategoryByName(@RequestParam(name="categoryname") String categoryname)
	{
		return categoryService.getCategoryByName(categoryname);
	}
	
	@GetMapping("/categorybyid")
	public Optional<Category> getCategoryById(@RequestParam(name="categoryid") Long categoryid)
	{
		return categoryService.getCategoryById(categoryid);
	}
	
	@GetMapping("/allcategory")
	public List<Category> getAllCategory()
	{
		return categoryService.getAllCategory();
	}
	
	//delete
	@DeleteMapping("deletecategorybyid")
	public void deletecategoryById(@RequestParam(name="categoryid") Long categoryid)
	{
	 categoryService.deleteCategoryById(categoryid);
	}
	
	@DeleteMapping("deletecategory")
	public void deletecategoryById(Category category)
	{
	 categoryService.deleteCategory(category);
	}
	
	@DeleteMapping("deleteallcategory")
	public void deleteAllcategory()
	{
	 categoryService.deleteAllCategory();
	}
	
	
	//************************  Operation's on Sub-Category ***********************************
	//create or update
		@PutMapping("/savesubcategory")
		public Subcategory saveSubCategory(@RequestBody Subcategory subcategory)
		{
			return subcategoryService.saveSubCategory(subcategory);
		}
		
		@PutMapping("/updatesubcategory")
		public Subcategory updateSubCategory(@RequestParam(name="subcategoryid")Long subcategoryid,@RequestBody Subcategory subcategory)
		{
			return subcategoryService.updateSubCategory(subcategoryid,subcategory);
		}
		
		
		//read
		@GetMapping("/subcategorybyname")
		public List<Subcategory> getSubCategoryByName(@RequestParam(name="subcategoryname") String subcategoryname)
		{
			return subcategoryService.getSubCategoryByName(subcategoryname);
		}
		
		@GetMapping("/subcategorybyid")
		public Optional<Subcategory> getSubCategoryById(@RequestParam(name="subcategoryid") Long subcategoryid)
		{
			return subcategoryService.getSubCategoryById(subcategoryid);
		}
		
		@GetMapping("/allsubcategory")
		public List<Subcategory> getAllSubCategory()
		{
			return subcategoryService.getAllSubCategory();
		}
		
		//delete
		@DeleteMapping("deletesubcategorybyid")
		public void deleteSubCategoryById(@RequestParam(name="subcategoryid") Long subcategoryid)
		{
		 subcategoryService.deleteSubCategoryById(subcategoryid);
		}
		
		@DeleteMapping("deletesubcategory")
		public void deleteSubcategoryById(Subcategory subcategory)
		{
		 subcategoryService.deleteSubCategory(subcategory);
		}
		
		@DeleteMapping("deleteallsubcategory")
		public void deleteAllSubcategory()
		{
		 subcategoryService.deleteAllSubCategory();
		}
			

		//************************  Operation's on Menu-Item ***********************************
		//create or update
		@PutMapping("/savemenuitem")
		public MenuItem saveMenuItem(@RequestBody MenuItem menuItem)
		{
			return menuItemService.saveMenuItem(menuItem);
		}
		
		@PutMapping("/updatemenuitem")
		public MenuItem updateMenuItem(@RequestParam(name="menuid")Long menuid,@RequestBody MenuItem menuItem)
		{
			return menuItemService.updateMenuItem(menuid,menuItem);
		}
		
		
		//read
		@GetMapping("/menuitembyname")
		public List<MenuItem> getMenuItemByName(@RequestParam(name="menuItemname") String menuItemname)
		{
			return menuItemService.getMenuItemByName(menuItemname);
		}
		
		@GetMapping("/menuitembyid")
		public Optional<MenuItem> getMenuItemById(@RequestParam(name="menuItemid") Long menuItemid)
		{
			return menuItemService.getMenuItemById(menuItemid);
		}
		
		@GetMapping("/allmenuitem")
		public List<MenuItem> getAllMenuItem()
		{
			return menuItemService.getAllMenuItem();
		}
		
		//delete
		@DeleteMapping("deletemenuitembyid")
		public void deleteMenuItem(@RequestParam(name="menuItemid") Long menuItemid)
		{
		 menuItemService.deleteMenuItemById(menuItemid);
		}
		
		@DeleteMapping("deletemenuitem")
		public void deleteMenuItemById(MenuItem menuItem)
		{
		 menuItemService.deleteMenuItem(menuItem);
		}
		
		@DeleteMapping("deleteallmenuitem")
		public void deleteAllMenuItem()
		{
		 menuItemService.deleteAllMenuItem();
		}

		
}
