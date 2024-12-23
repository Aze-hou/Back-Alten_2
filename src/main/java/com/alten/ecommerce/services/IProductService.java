package com.alten.ecommerce.services;

import java.util.List;

import com.alten.ecommerce.dto.ProductDto;

public interface IProductService {

	List<ProductDto> getAll();

	ProductDto save(ProductDto productDto) throws Exception;

	ProductDto update(ProductDto productDto, Integer id) throws Exception;

	void delete(Integer id);
}
