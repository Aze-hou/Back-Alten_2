package com.alten.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.ecommerce.dto.ProductDto;
import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.repository.ProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	// all product
	public List<ProductDto> getAll() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(Product::getDto).collect(Collectors.toList());
	}

	// create product
	public ProductDto save(ProductDto productDto) throws Exception {
		Product product = this.dtoToItem(productDto, new Product());
		return productRepository.save(product).getDto();
	}

	// update product
	@Override
	public ProductDto update(ProductDto productDto, Integer id) throws Exception {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isPresent()) {
			Product product = this.dtoToItem(productDto, optionalProduct.get());
			return productRepository.save(product).getDto();
		} else
			throw new Exception("Product not found");
	}

	// dto To Item
	private Product dtoToItem(ProductDto productDto, Product product) {
		product.setId(productDto.getId());
		product.setCode(productDto.getCode());
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setQuantity(productDto.getQuantity());
		product.setInternalReference(productDto.getInternalReference());
		product.setShellId(productDto.getShellId());
		product.setInventoryStatus(productDto.getInventoryStatus());
		product.setRating(productDto.getRating());
		return product;
	}

	// delete Product
	public void delete(Integer id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isPresent()) {
			productRepository.deleteById(id);
		}
	}

}
