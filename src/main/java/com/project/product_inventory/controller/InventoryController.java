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

import com.project.product_inventory.models.InventoryModel;
import com.project.product_inventory.service.IInventoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "Inventory Resource")
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private IInventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Inserta el inventario")
    public ResponseEntity<?> saveInventory(@RequestBody InventoryModel request) {
        log.info("[InventoryController] -> saveInventory [" + request.getProduct().getName() + "]");

        return inventoryService.saveInventory(request);

    }

    @GetMapping
    @Operation(summary = "Lista inventarios")
    public ResponseEntity<?> listInventories() {
        log.info("[InventoryController] -> listInventories");

        return inventoryService.listInventories();
    }

    @GetMapping("/find")
    @Operation(summary = "Busca inventario por el id")
    public ResponseEntity<?> getInventoryByID(@PathParam("inventoryID") Long inventoryID) {
        log.info("[InventoryController] -> getInventoryByID [" + inventoryID + "]");

        return inventoryService.getInventoryByID(inventoryID);
    }

    @PutMapping("/{productName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Actualiza Inventario")
    public ResponseEntity<?> updateInventory(@PathVariable("productName") String productName,
            @RequestBody InventoryModel request) {
        log.info("[InventoryController] -> updateInventory [" + request.getProduct() + "]");

        return inventoryService.updateInventory(productName, request);

    }

    @DeleteMapping("/{inventoryID}")
    @Operation(summary = "Elimina Inventario")
    public ResponseEntity<?> deleteInventory(@PathVariable("inventoryID") long inventoryID) {
        log.info("[InventoryController] -> deleteInventory [" + inventoryID + "]");

        return inventoryService.deleteInventory(inventoryID);

    }
}
