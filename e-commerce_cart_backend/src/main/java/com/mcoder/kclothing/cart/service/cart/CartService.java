package com.mcoder.kclothing.cart.service.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcoder.kclothing.cart.dto.CartDto;
import com.mcoder.kclothing.cart.dto.CartItemDto;
import com.mcoder.kclothing.cart.dto.ImageDto;
import com.mcoder.kclothing.cart.dto.ProductDto;
import com.mcoder.kclothing.cart.model.Cart;
import com.mcoder.kclothing.cart.model.CartItem;
import com.mcoder.kclothing.cart.model.User;
import com.mcoder.kclothing.cart.repository.CartItemRepository;
import com.mcoder.kclothing.cart.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements CartServiceInterface{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

   
    @Transactional
    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cart not found!"));
        BigDecimal totalAmount = cart.getTotalAmount();
          cart.setTotalAmount(totalAmount);
          return cartRepository.save(cart);
      }
   

    @Transactional
    @Override
      public void clearCart(Long id) {
         Cart cart = getCart(id);
         cartItemRepository.deleteAllByCartId(id);
         cart.getItems().clear();
         cart.setTotalAmount(BigDecimal.ZERO);
         cartRepository.deleteById(id);
      }


    @Override
    public BigDecimal getTotalPrice(Long id) {
       Cart cart = getCart(id);
       return cart.getTotalAmount();
    }


   @Override
   public Cart initializeNewCart(User user) {
      return Optional.ofNullable(getCartByUserId(user.getId()))
         .orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
         });
   }

   @Override
   public Cart getCartByUserId(Long userId) {
      return cartRepository.findByUserId(userId);
   }

   @Override
   public CartDto convertToDto(Cart cart) {
   CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());
        cartDto.setItems(cart.getItems()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toSet()));
        cartDto.setTotalAmount(cart.getTotalAmount());
        return cartDto;
   }

   private CartItemDto convertToDto(CartItem cartItem) {
    CartItemDto cartItemDto = new CartItemDto();
    cartItemDto.setItemId(cartItem.getId());
    cartItemDto.setQuantity(cartItem.getQuantity());
    cartItemDto.setUnitPrice(cartItem.getUnitPrice());


    if (cartItem.getProduct() != null) {
        ProductDto productDto = new ProductDto();
        productDto.setId(cartItem.getProduct().getId());
        productDto.setName(cartItem.getProduct().getName());
        productDto.setBrand(cartItem.getProduct().getBrand());
        productDto.setPrice(cartItem.getProduct().getPrice());
        productDto.setQuantity(cartItem.getProduct().getQuantity());
        productDto.setDescription(cartItem.getProduct().getDescription());
        productDto.setCategory(cartItem.getProduct().getCategory());

        if (cartItem.getProduct().getImages() != null) {
         List<ImageDto> imageDtos = cartItem.getProduct().getImages().stream()
             .map(image -> new ImageDto(image.getId(), image.getFileName(), image.getDownloadUrl()))
             .collect(Collectors.toList());
         productDto.setImages(imageDtos);
     }
     
        cartItemDto.setProduct(productDto);
    }

    return cartItemDto;
}
}
