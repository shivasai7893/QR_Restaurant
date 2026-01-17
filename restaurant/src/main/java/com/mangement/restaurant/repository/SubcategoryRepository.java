package com.mangement.restaurant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mangement.restaurant.model.Category;
import com.mangement.restaurant.model.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

	// Create //Update

	Subcategory save(Subcategory Subcategory);

	// Read

	List<Subcategory> findByName(String name);

	List<Subcategory> findAll();

	Optional<Subcategory> findById(Long id);

	// Delete

	void deleteById(Long id);

	void delete(Subcategory Subcategory);

	void deleteAll();

}
