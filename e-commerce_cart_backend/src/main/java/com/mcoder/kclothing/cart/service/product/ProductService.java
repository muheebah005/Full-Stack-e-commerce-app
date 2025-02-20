package com.mcoder.kclothing.cart.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcoder.kclothing.cart.dto.ImageDto;
import com.mcoder.kclothing.cart.dto.ProductDto;
import com.mcoder.kclothing.cart.model.Category;
import com.mcoder.kclothing.cart.model.Image;
import com.mcoder.kclothing.cart.model.Product;
import com.mcoder.kclothing.cart.repository.CategoryRepository;
import com.mcoder.kclothing.cart.repository.ImageRepository;
import com.mcoder.kclothing.cart.repository.ProductRepository;
import com.mcoder.kclothing.cart.request.AddProductRequest;
import com.mcoder.kclothing.cart.request.ProductRequest;

@Service
@Transactional
public class ProductService implements ProductServiceInterface{
   
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest addProductRequest) {
        
        if (productExist(addProductRequest.getName(), addProductRequest.getBrand())) {
            throw new IllegalArgumentException(addProductRequest.getBrand() + " " + addProductRequest.getName() + " already exist and can only be updated!");
        }

    Category category = Optional.ofNullable(categoryRepository.findByName(addProductRequest.getCategory().getName()))
                 .orElseGet(() -> {
                    Category newCategory = new Category(addProductRequest.getCategory().getName());
                    return categoryRepository.save(newCategory);
                    });
                addProductRequest.setCategory(category);
                return productRepository.save(createProduct(addProductRequest, category));
       
    }


    private boolean productExist(String name, String brand){
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductRequest addProductRequest, Category category){
        return new Product(
            addProductRequest.getName(),
            addProductRequest.getBrand(),
            addProductRequest.getPrice(),
            addProductRequest.getQuantity(),
            addProductRequest.getDescription(),
            category
        );
    }



    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Product not found!"));
    }



    @Override
    public void deleteProductById(Long id) {
       productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () ->
                 {throw new IllegalStateException("product not found!");});
    }



    @Override
    public Product updateProduct(ProductRequest productRequest, Long productId) {

       return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, productRequest))
                .map(productRepository :: save)
                .orElseThrow(() -> new IllegalStateException("product not found!"));
    }



    private Product updateExistingProduct(Product existingProduct, ProductRequest productRequest){
        existingProduct.setName(productRequest.getName());
        existingProduct.setBrand(productRequest.getBrand());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setDescription(productRequest.getDescription());

        Category category = categoryRepository.findByName(productRequest.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }



    @Override
    public List<Product> getAllProducts() {
       return productRepository.findAll();
    }


    @Override
    public List<Product> getProductsByCategory(String category) {
      return productRepository.findByCategoryName(category);
    }



    @Override
    public List<Product> getProductsByBrand(String brand) {
       return productRepository.findByBrand(brand);
    }



    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
       return productRepository.findByCategoryNameAndBrand(category, brand);
    }



    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }



    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }


    
    @Override
    public Long countProductByNameAndBrand(String name, String brand) {
        return productRepository.countByNameAndBrand(name, brand);
    }

    @Override
    public List<ProductDto> getConvertedProduct(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    
    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image,ImageDto.class)).toList();
            productDto.setImages(imageDtos);
            return productDto;
    }
}
