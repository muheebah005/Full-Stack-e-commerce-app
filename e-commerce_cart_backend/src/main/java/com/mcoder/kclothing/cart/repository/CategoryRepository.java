package com.mcoder.kclothing.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcoder.kclothing.cart.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Category findByName(String name);

    boolean existsByName(String name);

}
