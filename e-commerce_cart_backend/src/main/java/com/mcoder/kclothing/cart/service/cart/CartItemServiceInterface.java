package com.mcoder.kclothing.cart.service.cart;

import com.mcoder.kclothing.cart.model.CartItem;

public interface CartItemServiceInterface {

    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long itemId);
    void updateItemQuantity(Long cartId, Long itemId, int quantity);
    CartItem getCartItem(Long cartId, Long productId);
}
