package com.mcoder.kclothing.cart.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mcoder.kclothing.cart.dto.ImageDto;
import com.mcoder.kclothing.cart.model.Image;

public interface ProductImageServiceInterface {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> savedImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
