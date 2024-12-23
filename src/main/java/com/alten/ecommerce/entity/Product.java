package com.alten.ecommerce.entity;

import com.alten.ecommerce.dto.ProductDto;
import com.alten.ecommerce.dto.ProductDto.InventoryStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String code;
	private String name;
	private String description;
	private String image;
	private String category;
	private double price;
	private Integer quantity;
	private String internalReference;
	private long shellId;

	@Enumerated(EnumType.STRING)
	private InventoryStatus inventoryStatus;
	private Double rating;

	public ProductDto getDto() {
		ProductDto productDto = new ProductDto();
		productDto.setId(id);
		productDto.setCode(code);
		productDto.setName(name);
		productDto.setDescription(description);
		productDto.setPrice(price);
		productDto.setQuantity(quantity);
		productDto.setInternalReference(internalReference);
		productDto.setShellId(shellId);
		productDto.setInventoryStatus(inventoryStatus);
		productDto.setRating(rating);

		return productDto;
	}

}
