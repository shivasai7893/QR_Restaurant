package com.mangement.restaurant.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mangement.restaurant.security.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used by login — find user by email
    Optional<User> findByEmail(String email);

    // Check if email already registered
    boolean existsByEmail(String email);
}
