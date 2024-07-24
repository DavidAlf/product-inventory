package com.project.product_inventory.service;

import org.springframework.http.ResponseEntity;

import com.project.product_inventory.models.CategoryModel;

public interface ICategoryService {

    ResponseEntity<?> saveCategory(CategoryModel category);

    public ResponseEntity<?> listCategories();

    public ResponseEntity<?> getCategoryByName(String name);

    ResponseEntity<?> updateCategory(String name, CategoryModel category);

    ResponseEntity<?> deleteCategory(Long categoryID);
}
