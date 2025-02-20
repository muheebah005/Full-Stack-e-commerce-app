package com.mcoder.kclothing.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private Long imageId;
    private String imageName;
    private String downloadUrl;
}
