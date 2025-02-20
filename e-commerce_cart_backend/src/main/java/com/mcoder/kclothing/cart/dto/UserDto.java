package com.mcoder.kclothing.cart.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
