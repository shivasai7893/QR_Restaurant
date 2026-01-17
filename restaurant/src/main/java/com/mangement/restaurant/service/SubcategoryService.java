package com.mangement.restaurant.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.mangement.restaurant.model.Category;
import com.mangement.restaurant.model.Subcategory;
import com.mangement.restaurant.repository.CategoryRepository;
import com.mangement.restaurant.repository.SubcategoryRepository;

@Service
public class SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public SubcategoryService(SubcategoryRepository subcategoryRepository,CategoryRepository categoryRepository)
    {
    	this.subcategoryRepository=subcategoryRepository;
    	this.categoryRepository=categoryRepository;
    }
    
  //*************************Create and Update*************************
	
    public Subcategory saveSubCategory(Subcategory subcategory) {
        // Ensure category is set
    	 // System.out.println("checking the subcategory "+subcategory.getCategory().getId());
        Category category = categoryRepository.findById(subcategory.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        System.out.println("checking the category "+category.toString());

        subcategory.setCategory(category);
        return subcategoryRepository.save(subcategory);
    }

  	
  	public Subcategory updateSubCategory(Long id,Subcategory subcategory)
  	{
  		 return subcategoryRepository.findById(id).map(existingCategory -> {
  		        existingCategory.setName(subcategory.getName());
  		        existingCategory.setCategory(subcategory.getCategory());
  		        return subcategoryRepository.save(existingCategory);  // Saves only if the latest version is present
  		    }).orElseThrow(() -> new RuntimeException("SubcategoryRepository not found with ID: " + id));
  	}
  	
  	// *************************  Read *************************
  	public List<Subcategory> getSubCategoryByName(String name)
  	{
          return  subcategoryRepository.findByName(name);
  	}
  	
  	public List<Subcategory> getAllSubCategory()
  	{
          return  subcategoryRepository.findAll();
  	}
  	
  	public Optional<Subcategory> getSubCategoryById(Long id)
  	{
          return  subcategoryRepository.findById(id);
  	}
  	
  	// *************************  delete *************************
  	
  	public void deleteSubCategoryById(Long id)
  	{
  		 subcategoryRepository.deleteById(id);
  	}

  	public void deleteSubCategory(Subcategory subcategory)
  	{
  		 subcategoryRepository.delete(subcategory);
  	}
  	
  	public void deleteAllSubCategory()
  	{
  	   subcategoryRepository.deleteAll();
  	}

}
