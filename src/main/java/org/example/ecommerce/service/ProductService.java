package org.example.ecommerce.service;

import org.example.ecommerce.dto.ProductRequest;
import org.example.ecommerce.dto.response.ProductResponse;
import org.example.ecommerce.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);

    List<ProductResponse> getProductByCategory(Long categoryId);

    Product createProduct(ProductRequest request);

    void updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}
