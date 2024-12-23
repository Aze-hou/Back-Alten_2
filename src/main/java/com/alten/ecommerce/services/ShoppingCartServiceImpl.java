package com.alten.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alten.ecommerce.dto.CardItemDto;
import com.alten.ecommerce.dto.CardProductDto;
import com.alten.ecommerce.dto.UserCardsDto;
import com.alten.ecommerce.entity.CardProduct;
import com.alten.ecommerce.entity.CardStatus;
import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.entity.User;
import com.alten.ecommerce.entity.UserCards;
import com.alten.ecommerce.repository.ProductRepository;
import com.alten.ecommerce.repository.UserCardRepository;
import com.alten.ecommerce.security.SecurityUtil;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private UserCardRepository shoppingCartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SecurityUtil securityUtil;

	// add product to cart
	public void addProductToCart(CardItemDto cardItemDto) {
		Product product = this.checkProduct(cardItemDto.getProductId());
		User user = securityUtil.getCurrentUser();

		UserCards cardEntity = null;

		Optional<UserCards> card = shoppingCartRepository.getOpenedCard(CardStatus.PENDING, user.getId());
		if (card.isEmpty()) {
			cardEntity = new UserCards(Integer.valueOf(0), Double.valueOf(0.00), CardStatus.PENDING);
			cardEntity.setUser(user);
			this.shoppingCartRepository.save(cardEntity);
		} else {
			cardEntity = card.get();
		}

		CardProduct cardProduct = new CardProduct();
		cardProduct.setCard(cardEntity);
		cardProduct.setProduct(product);
		cardProduct.setQuantity(cardItemDto.getQuantity());
		cardProduct.setTotalPrice(product.getPrice() * cardItemDto.getQuantity());
		cardProduct.setUnitPrice(product.getPrice());
		cardEntity.getProducts().add(cardProduct);
		this.shoppingCartRepository.save(cardEntity);
	}

	// check Product
	private Product checkProduct(Integer productId) {
		Optional<Product> productOpt = productRepository.findById(productId);
		if (!productOpt.isPresent()) {
			throw new RuntimeException("Product not found");
		}
		return productOpt.get();
	}

	// Supprimer un produit du panier
	public void removeProductFromCart(Integer productId) throws Exception {
		User user = securityUtil.getCurrentUser();
		UserCards cart = shoppingCartRepository.getOpenedCard(CardStatus.PENDING, user.getId()).get();

		CardProduct product = cart.getProducts().stream().filter(obj -> obj.getProduct().getId().equals(productId))
				.findFirst().orElse(null);

		if (product != null) {
			cart.getProducts().remove(product);

			cart.setTotalPrice(cart.getTotalPrice() - product.getTotalPrice());
			List<CardProduct> updatedProducts = cart.getProducts().stream().filter(p -> !p.getId().equals(productId))
					.collect(Collectors.toList());
			cart.getProducts().clear();
			cart.getProducts().addAll(updatedProducts);
			shoppingCartRepository.save(cart);
		} else {
			throw new Exception("product not found");
		}

	}

	// Retrieve the shopping cart by ID
	public UserCardsDto getShoppingCartById() {
		User user = securityUtil.getCurrentUser();
		UserCardsDto userCardsDto = new UserCardsDto();

		UserCards cardEntity = shoppingCartRepository.getOpenedCard(CardStatus.PENDING, user.getId()).get();
		userCardsDto.setTotalPrice(cardEntity.getTotalPrice());
		userCardsDto.setTotalProducts(cardEntity.getTotalProducts());
		userCardsDto.setStatus(cardEntity.getStatus());
		for (CardProduct product : cardEntity.getProducts()) {
			CardProductDto cardDto = new CardProductDto();
			cardDto.setQuantity(product.getQuantity());
			cardDto.setTotalPrice(product.getTotalPrice());
			cardDto.setUnitPrice(product.getUnitPrice());
			userCardsDto.getProducts().add(cardDto);
		}
		return userCardsDto;

	}
}
