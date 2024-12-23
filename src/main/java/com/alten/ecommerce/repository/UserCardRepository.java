package com.alten.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alten.ecommerce.entity.CardStatus;
import com.alten.ecommerce.entity.UserCards;

@Repository
public interface UserCardRepository extends JpaRepository<UserCards, Long> {

	@Query("SELECT uc FROM UserCards uc WHERE uc.status = :status AND uc.user.id = :userId")
	Optional<UserCards> getOpenedCard(@Param("status") CardStatus status, @Param("userId") Integer userId);
}
