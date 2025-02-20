package com.mcoder.kclothing.cart.service.order;

import java.util.List;

import com.mcoder.kclothing.cart.dto.OrderDto;
import com.mcoder.kclothing.cart.model.Order;

public interface OrderServiceInterface {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto convertToDto(Order order);
}
