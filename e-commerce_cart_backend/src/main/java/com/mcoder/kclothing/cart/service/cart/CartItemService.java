package com.mcoder.kclothing.cart.service.cart;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcoder.kclothing.cart.model.Cart;
import com.mcoder.kclothing.cart.model.CartItem;
import com.mcoder.kclothing.cart.model.Product;
import com.mcoder.kclothing.cart.repository.CartItemRepository;
import com.mcoder.kclothing.cart.repository.CartRepository;
import com.mcoder.kclothing.cart.service.product.ProductServiceInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemServiceInterface{

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductServiceInterface productService;

    @Autowired
    CartServiceInterface cartService;

    @Autowired
    CartRepository cartRepository;

    

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElse(new CartItem());
        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else{
            cartItem.setQuantity(cartItem.getQuantity()+ quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }


    @Transactional
    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToBeRemove = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cart.removeItem(itemToBeRemove);
        cartRepository.save(cart);
    }
    

    @Override
    public void updateItemQuantity(Long cartId, Long itemId, int quantity) {
         Cart cart = cartService.getCart(cartId);
         cart.getItems().stream()
           .filter(item -> item.getId().equals(itemId))  
          .findFirst().ifPresent(item -> {
             item.setQuantity(quantity);
             item.setUnitPrice(item.getProduct().getPrice());
             item.setTotalPrice();
         });
        BigDecimal totalAmount = cart.getItems().stream()
                                 .map(CartItem::getTotalPrice)
                                 .reduce(BigDecimal.ZERO, BigDecimal::add);
    cart.setTotalAmount(totalAmount);
    cartRepository.save(cart);
}



    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream()
        .filter(item -> item.getProduct().getId()
        .equals(productId)).findFirst()
        .orElseThrow(()-> new RuntimeException("Item not found!"));
    }
}
