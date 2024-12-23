package com.alten.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.alten.ecommerce.entity.User;

public interface UserDao extends JpaRepository<User, Integer> {

	User findByEmailId(@Param("email") String email);

	User findByEmail(String email);

	Optional<User> findById(Long cartId);
}
