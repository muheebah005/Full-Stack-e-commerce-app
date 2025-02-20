package com.mcoder.kclothing.cart.request;

import java.math.BigDecimal;

import com.mcoder.kclothing.cart.model.Category;

import lombok.Data;

@Data
public class AddProductRequest {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int quantity;
    private String description;
    private Category category;
}
