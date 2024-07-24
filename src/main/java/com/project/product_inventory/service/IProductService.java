package com.project.product_inventory.service;

import org.springframework.http.ResponseEntity;

import com.project.product_inventory.models.ProductModel;

public interface IProductService {

    ResponseEntity<?> saveProduct(ProductModel product);

    public ResponseEntity<?> listProducts();

    public ResponseEntity<?> getProductByName(String name);

    ResponseEntity<?> updateProduct(String name, ProductModel product);

    ResponseEntity<?> deleteProduct(Long productID);

}
