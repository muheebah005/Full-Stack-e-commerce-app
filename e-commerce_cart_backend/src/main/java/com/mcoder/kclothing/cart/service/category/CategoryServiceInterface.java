package com.mcoder.kclothing.cart.service.category;

import java.util.List;

import com.mcoder.kclothing.cart.model.Category;

public interface CategoryServiceInterface {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
