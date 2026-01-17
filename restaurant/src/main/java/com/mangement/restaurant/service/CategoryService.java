package com.mangement.restaurant.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mangement.restaurant.model.Category;
import com.mangement.restaurant.repository.CategoryRepository;

@Service
public class CategoryService {
	
	public final CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository)
	{
		this.categoryRepository=categoryRepository;	
	}
	
	//*************************Create and Update*************************
	
	public Category saveCategory(Category category)
	{
		return categoryRepository.save(category);
	}
	
	public Category updateCategory(Long id,Category category)
	{
		 return categoryRepository.findById(id).map(existingCategory -> {
		        existingCategory.setName(category.getName());
		        existingCategory.setDescription(category.getDescription());
		        return categoryRepository.save(existingCategory);  // Saves only if the latest version is present
		    }).orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
	}
	
	// *************************  Read *************************
	public List<Category> getCategoryByName(String name)
	{
        return  categoryRepository.findByName(name);
	}
	
	public List<Category> getAllCategory()
	{
        return  categoryRepository.findAll();
	}
	
	public Optional<Category> getCategoryById(Long id)
	{
        return  categoryRepository.findById(id);
	}
	
	// *************************  delete *************************
	
	public void deleteCategoryById(Long id)
	{
		 categoryRepository.deleteById(id);
	}

	public void deleteCategory(Category category)
	{
		 categoryRepository.delete(category);
	}
	
	public void deleteAllCategory()
	{
	   categoryRepository.deleteAll();
	}

}
