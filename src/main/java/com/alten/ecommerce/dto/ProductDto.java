package com.alten.ecommerce.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductDto {

	private Integer id;
	private String code;
	private String name;
	private String description;
	private String image;
	private String category;
	private double price;
	private int quantity;
	private String internalReference;
	private long shellId;
	private InventoryStatus inventoryStatus;
	private double rating;
	private Date createdAt;
	private Date updatedAt;

	// Enum for inventory status
	public enum InventoryStatus {
		INSTOCK, LOWSTOCK, OUTOFSTOCK
	}

}
