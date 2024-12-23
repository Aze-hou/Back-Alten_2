package com.alten.ecommerce.dto;

import lombok.Data;

@Data
public class WishListDto {

	private Long userId;
	private Integer productId;
	private Long id;
	private String productName;
	private Long price;
}
