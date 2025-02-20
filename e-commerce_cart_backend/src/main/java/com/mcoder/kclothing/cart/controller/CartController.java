package com.mcoder.kclothing.cart.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mcoder.kclothing.cart.dto.CartDto;
import com.mcoder.kclothing.cart.model.Cart;
import com.mcoder.kclothing.cart.response.ApiResponse;
import com.mcoder.kclothing.cart.service.cart.CartServiceInterface;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    @Autowired
    private CartServiceInterface cartService;


    @GetMapping("/{cartId}/mycart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
           Cart cart = cartService.getCart(cartId);
           CartDto cartDto = cartService.convertToDto(cart);
           return ResponseEntity.ok(new ApiResponse(true, "Success", cartDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Not Found!", null));
        }
    }
    

    
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
       try {
         cartService.clearCart(cartId);
         return ResponseEntity.ok(new ApiResponse(true, "Cart Cleared!", null));
       } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"Not Found", null));
       }
    }



    @GetMapping("/{cartId}/totalprice")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
       try {
         BigDecimal totalPrice = cartService.getTotalPrice(cartId);
         return ResponseEntity.ok(new ApiResponse(true, "Total Price", totalPrice));
       } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Cart Not Found!", null));
       }
    }
}
