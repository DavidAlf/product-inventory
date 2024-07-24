package com.project.product_inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.product_inventory.models.InventoryModel;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, Long> {

    @Query("SELECT i FROM InventoryModel i WHERE i.product.name = :productName")
    Optional<InventoryModel> findByProductName(@Param("productName") String productName);

}
