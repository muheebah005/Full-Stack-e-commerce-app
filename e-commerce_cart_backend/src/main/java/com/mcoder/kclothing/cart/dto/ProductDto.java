package com.mcoder.kclothing.cart.dto;

import java.math.BigDecimal;
import java.util.List;
import com.mcoder.kclothing.cart.model.Category;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int quantity;
    private String description;
    private Category category;
    private List<ImageDto> images;
}
