package com.petalsandproduce.backend.repository;

import com.petalsandproduce.backend.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByStockLessThanEqual(int threshold);

    List<Product> findByStock(int stock);
    
    List<Product> findByStockGreaterThan(int stock);
    
    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.stock > 0")
    List<Product> findAvailableProductsByCategory(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> findAllAvailableProducts();
    

    @Query("SELECT CASE WHEN p.stock >= :requiredQuantity THEN true ELSE false END FROM Product p WHERE p.id = :productId")
    boolean hassufficientStock(@Param("productId") Long productId, @Param("requiredQuantity") int requiredQuantity);
}