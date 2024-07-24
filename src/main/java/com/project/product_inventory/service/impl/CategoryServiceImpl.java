package com.project.product_inventory.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.product_inventory.exception.ResourceNotFundExcepton;
import com.project.product_inventory.models.CategoryModel;
import com.project.product_inventory.repository.CategoryRepository;
import com.project.product_inventory.service.ICategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> saveCategory(CategoryModel category) {
        log.info("[CategoryServiceImpl]=> saveCategory");

        try {
            Optional<CategoryModel> categorySaved = categoryRepository.findByName(category.getName());

            if (categorySaved.isPresent()) {
                throw new ResourceNotFundExcepton("La categoria ya existe: " + categorySaved.get().getName());
            }

            categoryRepository.save(category);

            return ResponseEntity.ok(category);

        } catch (Exception e) {
            log.error("Error saveCategory " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error guardando la categria.");
        }
    }

    @Override
    public ResponseEntity<?> listCategories() {
        log.info("[CategoryServiceImpl]=> listCategories");

        try {
            List<CategoryModel> listCategories = categoryRepository.findAll();

            return ResponseEntity.ok(listCategories);

        } catch (Exception e) {
            log.error("Error listCategories " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error listando las categorias.");
        }
    }

    @Override
    public ResponseEntity<?> getCategoryByName(String name) {
        log.info("[CategoryServiceImpl]=> getCategoryByName");

        try {
            Optional<CategoryModel> categorySaved = categoryRepository.findByName(name);

            return ResponseEntity.ok(categorySaved);
        } catch (Exception e) {
            log.error("Error getCategoryByName " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error buscando la categoria [" + name + "].");
        }
    }

    @Override
    public ResponseEntity<?> updateCategory(String name, CategoryModel category) {
        log.info("[CategoryServiceImpl]=> updateCategory");

        try {

            return categoryRepository.findByName(category.getName())
                    .map(categorySaved -> {
                        categorySaved.setName(category.getName());

                        CategoryModel updatedCategory = categoryRepository.save(categorySaved);

                        return ResponseEntity.ok(updatedCategory);
                    })
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            log.error("Error updateCategory " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error actualizando la Categoria[" + category.getName() + "].");
        }
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long categoryID) {
        log.info("[CategoryServiceImpl]=> deleteCategory");

        return categoryRepository.findById(categoryID).map(
                categorySaved -> {
                    categoryRepository.delete(categorySaved);

                    return ResponseEntity.ok("Categoria [" + categoryID + "] Eliminada correctamente.");
                }).orElse(ResponseEntity.notFound().build());
    }

}
