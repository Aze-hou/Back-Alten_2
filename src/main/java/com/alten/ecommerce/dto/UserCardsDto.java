package com.alten.ecommerce.dto;

import java.util.ArrayList;
import java.util.List;

import com.alten.ecommerce.entity.CardStatus;

import lombok.Data;

@Data
public class UserCardsDto {

	private Integer totalProducts;
	private Double totalPrice;
	private List<CardProductDto> products = new ArrayList<CardProductDto>();
	private CardStatus status;

}
