package com.mcoder.kclothing.cart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mcoder.kclothing.cart.model.Category;
import com.mcoder.kclothing.cart.response.ApiResponse;
import com.mcoder.kclothing.cart.service.category.CategoryServiceInterface;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryServiceInterface categoryService;



    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
       try {
         List<Category> categories = categoryService.getAllCategories();
         return ResponseEntity.ok(new ApiResponse(true, "found", categories));
       } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Error!"));
       }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try {
            Category category = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse(true, "success!", category));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, "Category already exist!"));
        }
    }

    @GetMapping("/category/{id}/get")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Found!", category));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Not found!", null));
        }
    }
    



    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse(true, "Found!", category));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Not found!", null));
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
   @PutMapping("/category/{id}/update")
   public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
    try {
        Category updateCategory = categoryService.updateCategory(category, id);
        return ResponseEntity.ok(new ApiResponse(true, "updated successfully!", updateCategory));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "not found", null));
    }
   }


   @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Deleted!"));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Not found!", null));
        }
    }
}
