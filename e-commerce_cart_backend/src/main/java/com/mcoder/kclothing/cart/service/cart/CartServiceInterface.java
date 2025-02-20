package com.mcoder.kclothing.cart.service.cart;

import java.math.BigDecimal;

import com.mcoder.kclothing.cart.dto.CartDto;
import com.mcoder.kclothing.cart.model.Cart;
import com.mcoder.kclothing.cart.model.User;

public interface CartServiceInterface {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);
    CartDto convertToDto(Cart cart);
}
