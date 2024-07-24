package com.project.product_inventory.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.product_inventory.exception.ResourceNotFundExcepton;
import com.project.product_inventory.models.InventoryModel;
import com.project.product_inventory.models.ProductModel;
import com.project.product_inventory.repository.InventoryRepository;
import com.project.product_inventory.repository.ProductRepository;
import com.project.product_inventory.service.IInventoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InventoryServiceImpl implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> saveInventory(InventoryModel inventory) {
        log.info("[InventoryServiceImpl]=> saveInventory");

        try {
            Optional<ProductModel> productSaved = productRepository.findByName(inventory.getProduct().getName());

            if (!productSaved.isPresent()) {
                throw new ResourceNotFundExcepton("El producto no existe: " +
                        inventory.getProduct().getName());
            }

            Optional<InventoryModel> inventorySaved = inventoryRepository
                    .findByProductName(inventory.getProduct().getName());

            if (inventorySaved.isPresent()) {
                throw new ResourceNotFundExcepton("El inventario ya existe: " + inventorySaved.get().getProduct());
            }

            inventory.setProduct(productSaved.get());
            inventoryRepository.save(inventory);

            return ResponseEntity.ok(inventory);

        } catch (Exception e) {
            log.error("Error saveInventory " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error guardando el inventario.");
        }
    }

    @Override
    public ResponseEntity<?> listInventories() {
        log.info("[InventoryServiceImpl]=> listInventories");

        try {
            List<InventoryModel> listInventories = inventoryRepository.findAll();

            return ResponseEntity.ok(listInventories);

        } catch (Exception e) {
            log.error("Error listInventories " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error listando los inventarios.");
        }
    }

    @Override
    public ResponseEntity<?> getInventoryByID(Long inventoryID) {
        log.info("[InventoryServiceImpl]=> getInventoryByID");

        try {
            Optional<InventoryModel> inventorySaved = inventoryRepository.findById(inventoryID);

            return ResponseEntity.ok(inventorySaved);
        } catch (Exception e) {
            log.error("Error getInventoryByID " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error buscando el inventario [" + inventoryID + "].");
        }
    }

    @Override
    public ResponseEntity<?> updateInventory(String productName, InventoryModel inventory) {
        log.info("[InventoryServiceImpl]=> updateinventory");

        try {

            Optional<ProductModel> productSaved = productRepository.findByName(productName);

            if (!productSaved.isPresent()) {
                throw new ResourceNotFundExcepton("El producto no existe: " +
                        inventory.getProduct().getName());
            }

            return inventoryRepository.findByProductName(productName)
                    .map(inventorySaved -> {
                        inventorySaved.setDate(inventory.getDate());
                        inventorySaved.setQuantity(inventory.getQuantity());

                        InventoryModel updatedInventory = inventoryRepository.save(inventorySaved);

                        return ResponseEntity.ok(updatedInventory);
                    })
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            log.error("Error updateinventory " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Se produjo un error actualizando el inventario[" + inventory.getId() + "].");
        }
    }

    @Override
    public ResponseEntity<?> deleteInventory(Long inventoryID) {
        log.info("[InventoryServiceImpl]=> deleteinventory");

        return inventoryRepository.findById(inventoryID).map(
                inventorySaved -> {
                    inventoryRepository.delete(inventorySaved);

                    return ResponseEntity.ok("Inventario [" + inventoryID + "] Eliminado correctamente.");
                }).orElse(ResponseEntity.notFound().build());
    }

}
