package com.project.product_inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.product_inventory.models.CategoryModel;
import com.project.product_inventory.service.ICategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "Category Resource")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crea Categorias")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryModel request) {
        log.info("[CategoryController] -> saveCategory [" + request.getName() + "]");

        return categoryService.saveCategory(request);

    }

    @GetMapping
    @Operation(summary = "Lista Categorias")
    public ResponseEntity<?> listCategories() {
        log.info("[CategoryController] -> listCategories");

        return categoryService.listCategories();
    }

    @GetMapping("/find")
    @Operation(summary = "Busca Categoria por el nombre")
    public ResponseEntity<?> getCategoryByName(@PathParam("name") String name) {
        log.info("[CategoryController] -> getCategoryByName [" + name + "]");

        return categoryService.getCategoryByName(name);
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Actualiza Categoria")
    public ResponseEntity<?> updateCategory(@PathVariable("name") String name,
            @RequestBody CategoryModel request) {
        log.info("[CategoryController] -> updateCategory [" + request.getName() + "]");

        return categoryService.updateCategory(name, request);

    }

    @DeleteMapping("/{categoryID}")
    @Operation(summary = "Elimina Categoria")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryID") long categoryID) {
        log.info("[CategoryController] -> deleteCategory [" + categoryID + "]");

        return categoryService.deleteCategory(categoryID);

    }

}
