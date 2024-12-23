package com.alten.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.entity.User;
import com.alten.ecommerce.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
	List<Wishlist> findAllByUserId(Integer userId);

	Optional<Wishlist> findByProductIdAndUserId(Long productId, Long userId);

	boolean existsByProductAndUser(Product product, User user);

	Optional<Wishlist> findByProductAndUser(Product product, User user);

	@Modifying
	@Query("DELETE FROM Wishlist c WHERE c.product.id = :id")
	void deleteByProductId(@Param("id") Integer id);
}
