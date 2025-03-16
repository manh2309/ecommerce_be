package org.example.ecommerce.repository;

import org.example.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.id=:productId and p.isActive = true")
    Optional<Product> findProductById(@Param("productId") Long productId);
    Optional<Product> findByName(String name);
    List<Product> findByCategoryId(Long id);
}
