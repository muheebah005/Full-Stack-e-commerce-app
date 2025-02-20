package com.mcoder.kclothing.cart.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcoder.kclothing.cart.dto.OrderDto;
import com.mcoder.kclothing.cart.enums.OrderStatus;
import com.mcoder.kclothing.cart.model.Cart;
import com.mcoder.kclothing.cart.model.Order;
import com.mcoder.kclothing.cart.model.OrderItem;
import com.mcoder.kclothing.cart.model.Product;
import com.mcoder.kclothing.cart.repository.OrderRepository;
import com.mcoder.kclothing.cart.repository.ProductRepository;
import com.mcoder.kclothing.cart.service.cart.CartServiceInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartServiceInterface cartService;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = creatOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
       return savedOrder;
    }


    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                order,
                product,
                cartItem.getQuantity(),
                cartItem.getUnitPrice()
            );
        }).toList();
    }

    private Order creatOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems){
        return orderItems.stream().map(item -> item.getPrice()
            .multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal :: add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
       return orderRepository.findById(orderId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
    }


    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> order = orderRepository.findByUserId(userId);
        return order.stream().map(this :: convertToDto).toList();
    }

    @Override
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
