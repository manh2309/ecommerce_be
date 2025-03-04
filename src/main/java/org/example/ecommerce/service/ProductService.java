package org.example.ecommerce.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.ecommerce.dto.ProductRequest;
import org.example.ecommerce.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);

    List<Product> getProductByCategory(Long categoryId);

    Product createProduct(ProductRequest request);

    void updateProduct(Product product);

    void deleteProduct(Long id);
}
