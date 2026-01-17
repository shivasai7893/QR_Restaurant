package com.mangement.restaurant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mangement.restaurant.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	//Create //Update
	
	Category save(Category category);
	
	//Read

	List<Category> findByName(String name);
	
	List<Category> findAll();
	
	Optional<Category> findById(Long id);
	
	//Delete
	
	void deleteById(Long id);
	
	void delete(Category category);
	
	void deleteAll();
	
}
