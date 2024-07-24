package com.project.product_inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.product_inventory.models.CategoryModel;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    Optional<CategoryModel> findByName(@Param(("name")) String name);

}
