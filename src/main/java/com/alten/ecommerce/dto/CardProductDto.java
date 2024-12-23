package com.alten.ecommerce.dto;

import lombok.Data;

@Data
public class CardProductDto {

	private Integer id;
	private Integer quantity;
	private Double unitPrice;
	private Double totalPrice;

}
