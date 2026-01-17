package com.mangement.restaurant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mangement.restaurant.model.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

	// Create //Update

	MenuItem save(MenuItem menuItem);

	// Read

	List<MenuItem> findByName(String name);

	List<MenuItem> findAll();

	Optional<MenuItem> findById(Long id);

	// Delete

	void deleteById(Long id);

	void delete(MenuItem menuItem);

	void deleteAll();

}
