package com.mcoder.kclothing.cart.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcoder.kclothing.cart.model.Category;
import com.mcoder.kclothing.cart.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInterface{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
       return categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("category not found!"));
    }


    @Override
    public Category getCategoryByName(String name) {
       return  categoryRepository.findByName(name);
    }


    @Override
    public List<Category> getAllCategories() {
       return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
       return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()-> new RuntimeException("category already exists!"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
            .map(oldCategory -> {
                oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }) .orElseThrow(()-> new RuntimeException("category not found!"));
    }

    @Override
    public void deleteCategoryById(Long id) {
       categoryRepository.findById(id)
            .ifPresentOrElse(categoryRepository::delete, ()-> {
                throw new RuntimeException("category not found");
            });
    }
}