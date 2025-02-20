package com.mcoder.kclothing.cart.service.product;

import java.util.List;

import com.mcoder.kclothing.cart.dto.ProductDto;
import com.mcoder.kclothing.cart.model.Product;
import com.mcoder.kclothing.cart.request.AddProductRequest;
import com.mcoder.kclothing.cart.request.ProductRequest;

public interface ProductServiceInterface {
    Product addProduct(AddProductRequest addProductRequest);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductRequest productRequest, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductByNameAndBrand(String name, String brand);
    ProductDto convertToDto(Product product);
    List<ProductDto> getConvertedProduct(List<Product> products);

}
