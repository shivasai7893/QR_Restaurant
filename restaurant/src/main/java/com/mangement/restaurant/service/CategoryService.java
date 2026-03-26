package com.mangement.restaurant.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mangement.restaurant.model.Category;
import com.mangement.restaurant.model.Subcategory;
import com.mangement.restaurant.repository.CategoryRepository;
import com.mangement.restaurant.repository.SubcategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	
	public final CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository)
	{
		this.categoryRepository=categoryRepository;	
	}
	
	//*************************Create and Update*************************
	
	 @Autowired
	    private SubcategoryRepository subcategoryRepository;
	
	 // Default subcategories to auto-create for every new category
    private static final List<String> DEFAULT_SUBCATEGORIES = 
        List.of("Veg", "Non-Veg", "All");

    @Transactional
    public Category saveCategory(Category category) {

        // Step 1 — Save the category first
        Category savedCategory = categoryRepository.save(category);

        // Step 2 — Auto-create default subcategories
        for (String subName : DEFAULT_SUBCATEGORIES) {
            Subcategory sub = new Subcategory();
            sub.setName(subName);
            sub.setCategory(savedCategory);   // link to new category
            subcategoryRepository.save(sub);
        }

        return savedCategory;
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
