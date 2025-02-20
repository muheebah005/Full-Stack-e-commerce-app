package com.mcoder.kclothing.cart.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mcoder.kclothing.cart.dto.ImageDto;
import com.mcoder.kclothing.cart.model.Image;
import com.mcoder.kclothing.cart.model.Product;
import com.mcoder.kclothing.cart.repository.ImageRepository;
import com.mcoder.kclothing.cart.service.product.ProductServiceInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductImageService implements ProductImageServiceInterface{

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductServiceInterface productServiceInterface;


    

    @Override
    public Image getImageById(Long id) {
       return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No image found with id: " + id));
    }




    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new RuntimeException("Image not found with id: "+ id);
        });
    }

    
    @Transactional
    @Override
    public List<ImageDto> savedImages(List<MultipartFile> files, Long productId) {
       
        Product product = productServiceInterface.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
         for(MultipartFile file : files){

            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl =  buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage =  imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

                
            } catch (IOException | SQLException e) {
              throw new RuntimeException("image not found!");
            }
         }
         return savedImageDto;
    }



    @Override
    public void updateImage(MultipartFile file, Long imageId) {
      Image image = getImageById(imageId);
      try {
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setImage(new SerialBlob(file.getBytes()));
        imageRepository.save(image);
      } catch (IOException | SQLException e) {
        throw new RuntimeException("image not found!");
      }
    }

}
