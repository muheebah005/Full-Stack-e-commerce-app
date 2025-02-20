package com.mcoder.kclothing.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcoder.kclothing.cart.dto.CartDto;
import com.mcoder.kclothing.cart.model.Cart;
import com.mcoder.kclothing.cart.model.User;
import com.mcoder.kclothing.cart.response.ApiResponse;
import com.mcoder.kclothing.cart.service.cart.CartItemServiceInterface;
import com.mcoder.kclothing.cart.service.cart.CartServiceInterface;
import com.mcoder.kclothing.cart.service.user.UserServiceInterface;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cartItems")
public class CartItemController {

    @Autowired
    private CartItemServiceInterface cartItemService;

    @Autowired
    private CartServiceInterface cartService;

    @Autowired
    private UserServiceInterface userService;


    @PostMapping("/item/add")
public ResponseEntity<ApiResponse> addItemToCart(
                                                @RequestParam Long productId,
                                                @RequestParam Integer quantity) {
    try {    
        User user = userService.getAuthenticatedUser();
        Cart cart = cartService.initializeNewCart(user);

        cartItemService.addItemToCart(cart.getId(), productId, quantity);
        CartDto cartDto = cartService.convertToDto(cart);
        return ResponseEntity.ok(new ApiResponse(true, "Item Added Successfully", cartDto));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new ApiResponse(false, "Not Found!", null));
    }
}



    @DeleteMapping("{cartId}/item/{itemId}/delete")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
    try {
        cartItemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse(true, "Item Removed Successfully", null));
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage(), null));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "An error occurred", null));
    }
}


    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, 
                                                          @PathVariable Long itemId, 
                                                          @RequestParam Integer quantity){
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse(true, "Item Updated Successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Not Found!", null));
        }
    }
}
