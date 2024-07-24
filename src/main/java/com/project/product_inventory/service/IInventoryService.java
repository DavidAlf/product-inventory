package com.project.product_inventory.service;

import org.springframework.http.ResponseEntity;

import com.project.product_inventory.models.InventoryModel;

public interface IInventoryService {

    ResponseEntity<?> saveInventory(InventoryModel category);

    public ResponseEntity<?> listInventories();

    public ResponseEntity<?> getInventoryByID(Long inventoryID);

    ResponseEntity<?> updateInventory(String productName, InventoryModel category);

    ResponseEntity<?> deleteInventory(Long inventoryID);
}
