package org.example.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dto.ProductRequest;
import org.example.ecommerce.entity.Brand;
import org.example.ecommerce.entity.Category;
import org.example.ecommerce.entity.Product;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.repository.BrandRepository;
import org.example.ecommerce.repository.CategoryRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSerivceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository  brandRepository;
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "product not found"));
    }

    @Override
    public List<Product> getProductByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public Product createProduct(ProductRequest request) {
        if(!productRepository.findByName(request.getName()).isEmpty()){
            throw new ApiException(HttpStatus.BAD_REQUEST, "product already exist");
        }
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "category not found"));
        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "brand not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(category);
        product.setBrand(brand);
        return productRepository.save(product);
        }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(Long id) {

    }
    }
