package com.project.product_inventory.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.product_inventory.exception.ResourceNotFundExcepton;
import com.project.product_inventory.models.CategoryModel;
import com.project.product_inventory.models.ProductModel;
import com.project.product_inventory.repository.CategoryRepository;
import com.project.product_inventory.repository.ProductRepository;
import com.project.product_inventory.service.IProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> saveProduct(ProductModel product) {
        log.info("[ProductServiceImpl]=> saveProduct");

        try {
            Optional<ProductModel> productSaved = productRepository.findByName(product.getName());

            if (productSaved.isPresent()) {
                throw new ResourceNotFundExcepton("El producto ya existe: " + productSaved.get().getName());
            }

            Optional<CategoryModel> categorySaved = categoryRepository.findByName(product.getCategory().getName());

            if (!categorySaved.isPresent()) {
                throw new ResourceNotFundExcepton("La categoria no existe: " + product.getCategory().getId());
            }

            product.setCategory(categorySaved.get());
            productRepository.save(product);

            return ResponseEntity.ok(product);

        } catch (Exception e) {
            log.error("Error saveProduct " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error guardando el producto.");
        }
    }

    @Override
    public ResponseEntity<?> listProducts() {
        log.info("[ProductServiceImpl]=> listProducts");

        try {
            List<ProductModel> listProducts = productRepository.findAll();

            return ResponseEntity.ok(listProducts);

        } catch (Exception e) {
            log.error("Error listProducts " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error listando los productos.");
        }
    }

    @Override
    public ResponseEntity<?> getProductByName(String name) {
        log.info("[ProductServiceImpl]=> getProductByName");

        try {
            Optional<ProductModel> productSaved = productRepository.findByName(name);

            return ResponseEntity.ok(productSaved);
        } catch (Exception e) {
            log.error("Error getProductByName " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error buscando el producto [" + name + "].");
        }
    }

    @Override
    public ResponseEntity<?> updateProduct(Long productID, ProductModel product) {
        log.info("[ProductServiceImpl]=> updateProduct");

        try {

            Optional<CategoryModel> categorySaved = categoryRepository.findByName(product.getCategory().getName());

            if (!categorySaved.isPresent()) {
                throw new ResourceNotFundExcepton("La categoria no existe: " + product.getCategory().getId());
            }

            return productRepository.findById(productID)
                    .map(productSaved -> {
                        productSaved.setName(product.getName());
                        productSaved.setDescription(product.getDescription());
                        productSaved.setPrice(product.getPrice());
                        productSaved.setCategory(categorySaved.get());

                        ProductModel updatedProduct = productRepository.save(productSaved);

                        return ResponseEntity.ok(updatedProduct);
                    })
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            log.error("Error updateProduct " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error actualizando el producto[" + product.getName() + "].");
        }
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long productID) {
        log.info("[ProductServiceImpl]=> deleteProduct");

        return productRepository.findById(productID).map(
                productSaved -> {
                    productRepository.delete(productSaved);

                    return ResponseEntity.ok("Producto [" + productID + "] Eliminado correctamente.");
                }).orElse(ResponseEntity.notFound().build());
    }

}
