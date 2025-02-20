package com.mcoder.kclothing.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcoder.kclothing.cart.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByNameAndBrand(String name, String brand);

    boolean existsByNameAndBrand(String name, String brand);

    

}
