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

import com.project.product_inventory.models.ProductModel;
import com.project.product_inventory.service.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "Product Resource")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crea Productos")
    public ResponseEntity<?> saveProduct(@RequestBody ProductModel request) {
        log.info("[ProductController] -> saveProduct [" + request.getName() + "]");

        return productService.saveProduct(request);

    }

    @GetMapping
    @Operation(summary = "Lista Productos")
    public ResponseEntity<?> listProducts() {
        log.info("[ProductController] -> listProducts");

        return productService.listProducts();
    }

    @GetMapping("/find")
    @Operation(summary = "Busca producto por el nombre")
    public ResponseEntity<?> getProductByName(@PathParam("name") String name) {
        log.info("[ProductController] -> getProductByName [" + name + "]");

        return productService.getProductByName(name);
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Actualiza Producto")
    public ResponseEntity<?> updateProduct(@PathVariable("name") String name, @RequestBody ProductModel request) {
        log.info("[ProductController] -> updateProduct [" + name + "]");

        return productService.updateProduct(name, request);

    }

    @DeleteMapping("/{productID}")
    @Operation(summary = "Elimina Producto")
    public ResponseEntity<?> deleteProduct(@PathVariable("productID") long productID) {
        log.info("[ProductController] -> deleteProduct [" + productID + "]");

        return productService.deleteProduct(productID);

    }
}
