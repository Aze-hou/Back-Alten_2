package com.alten.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.ecommerce.dto.ProductDto;
import com.alten.ecommerce.services.IProductService;
import com.alten.ecommerce.utils.AltenUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	@Autowired
	private IProductService productService;

	// list of products
	// Afficher toutes les informations pertinentes d'un produit sur la liste
	@GetMapping
	@CrossOrigin("*")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> productDtos = productService.getAll();
		return ResponseEntity.ok(productDtos);
	}

	// add Product
	// Permettre d'ajouter un produit au panier depuis la liste
	@PostMapping
	@CrossOrigin("*")
	public ResponseEntity<String> create(@RequestBody ProductDto productDto) throws Exception {
		ProductDto productDto1 = productService.save(productDto);
		return AltenUtils.getResponseEntity("Product Added successfully", HttpStatus.OK);
	}

	// update Product
	@PutMapping("/{id}")
	@CrossOrigin("*")
	public ResponseEntity<String> update(@PathVariable(name = "id") Integer id, @RequestBody ProductDto productDto)
			throws Exception {
		ProductDto productDto1 = productService.update(productDto, id);
		return AltenUtils.getResponseEntity("Product updated successfully", HttpStatus.OK);

	}

	// delete Product
	// Permettre de supprimer un produit du panier
	@DeleteMapping("/{id}")
	@CrossOrigin("*")
	public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Integer id) {
		productService.delete(id);
		return AltenUtils.getResponseEntity("Product deleted successfully", HttpStatus.OK);
	}
}
