package com.mcoder.kclothing.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcoder.kclothing.cart.dto.ProductDto;
import com.mcoder.kclothing.cart.model.Product;
import com.mcoder.kclothing.cart.request.AddProductRequest;
import com.mcoder.kclothing.cart.request.ProductRequest;
import com.mcoder.kclothing.cart.response.ApiResponse;
import com.mcoder.kclothing.cart.service.product.ProductServiceInterface;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductServiceInterface productService;

    
    @GetMapping("/all/products")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProduct(products);
        return ResponseEntity.ok(new ApiResponse(true, "successful!", convertedProducts));
    }

    
    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
       try {
         Product product = productService.getProductById(productId);
         ProductDto productDto = productService.convertToDto(product);
         
         return ResponseEntity.ok(new ApiResponse(true, "success", productDto));
       } catch (Exception e) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Product not found!", null));
       }
    }
    

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/product")
public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest addProductRequest) {
    try {
        Product productTwo = productService.addProduct(addProductRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Product successfully added!", productTwo));
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(new ApiResponse(false, e.getMessage(), null));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new ApiResponse(false, "An unexpected error occurred!", null));
    }
}


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductRequest request, @PathVariable Long productId){
      try {
        Product product = productService.updateProduct(request, productId);
        ProductDto productDto = productService.convertToDto(product);
        return ResponseEntity.ok(new ApiResponse(true, "Product successfully updated!", productDto));
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Failed to update Product!", null));
      }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
      try {
        productService.deleteProductById(productId);
        return ResponseEntity.ok(new ApiResponse(true, "Product deleted!"));
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Unable to delete product!", null));
      }
    }


    @GetMapping("/brand/name/product")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
      try {
        List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
        
        if (products.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "No product found!", null));
        }
        List<ProductDto> convertedProducts = productService.getConvertedProduct(products);
        return ResponseEntity.ok(new ApiResponse(true,"successful!",convertedProducts));
      } catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Not found!", null));
      }
    }


    @GetMapping("/category/brand/product")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String category, @PathVariable String brand){
      try {
        List<Product> products = productService.getProductsByBrandAndName(category, brand);
        List<ProductDto> convertedProducts = productService.getConvertedProduct(products);
        if (products.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "No product found!", null));
        }
        return ResponseEntity.ok(new ApiResponse(true,"successful!",convertedProducts));
      } catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Not found!", null));
      }
    }

    @GetMapping("/products/{name}/product")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
      try {
        List<Product> products = productService.getProductsByName(name);
        List<ProductDto> convertedProducts = productService.getConvertedProduct(products);
        if (products.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "No product found!", null));
        }
        return ResponseEntity.ok(new ApiResponse(true,"successful!",convertedProducts));
      } catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Not found!", null));
      }
    }

    @GetMapping("/brand/product/get")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){
      try {
        List<Product> products = productService.getProductsByBrand(brand);
        List<ProductDto> convertedProducts = productService.getConvertedProduct(products);
        if (products.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "No product found!", null));
        }
        return ResponseEntity.ok(new ApiResponse(true,"successful!",convertedProducts));
      } catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Not found!", null));
      }
    }

    @GetMapping("/products/{category}/all/product")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){
      try {
        List<Product> products = productService.getProductsByCategory(category);
        List<ProductDto> convertedProducts = productService.getConvertedProduct(products);
        if (products.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "No product found!", null));
        }
        return ResponseEntity.ok(new ApiResponse(true,"successful!",convertedProducts));
      } catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Not found!", null));
      }
    }

    @GetMapping("/products/count/product")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand, @RequestParam String name){
      try {
        var productCount = productService.countProductByNameAndBrand(brand, name);
        return ResponseEntity.ok(new ApiResponse(true,"successful!",productCount));
      } catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Not found!", null));
      }
    }
}
