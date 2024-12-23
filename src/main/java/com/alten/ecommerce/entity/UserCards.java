package com.alten.ecommerce.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class UserCards {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CardProduct> products = new ArrayList<CardProduct>();

	private Integer totalProducts;

	private Double totalPrice;

	public UserCards() {
		super();
	}

	public UserCards(Integer totalProducts, Double totalPrice, CardStatus status) {
		super();
		this.totalProducts = totalProducts;
		this.totalPrice = totalPrice;
		this.status = status;
	}

	@Enumerated(EnumType.STRING)
	private CardStatus status;

}
