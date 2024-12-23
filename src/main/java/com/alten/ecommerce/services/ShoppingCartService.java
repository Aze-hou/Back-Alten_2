package com.alten.ecommerce.services;

import com.alten.ecommerce.dto.CardItemDto;
import com.alten.ecommerce.dto.UserCardsDto;

public interface ShoppingCartService {

	public void addProductToCart(CardItemDto cardItemDto);

	public void removeProductFromCart(Integer productId) throws Exception;

	public UserCardsDto getShoppingCartById();

}
