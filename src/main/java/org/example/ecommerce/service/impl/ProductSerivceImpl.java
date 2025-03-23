package org.example.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.common.dto.ApiResponse;
import org.example.ecommerce.dto.ProductRequest;
import org.example.ecommerce.dto.response.ProductResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSerivceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository  brandRepository;
    @Override
    public Product getProductById(Long productId) {
        return productRepository.findProductById(productId).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "product not found"));
    }

    @Override
    public List<ProductResponse> getProductByCategory(Long categoryId) {
        List<ProductResponse> products = productRepository.findByCategoryId(categoryId).stream().map(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            return productResponse;
        }).toList();
        return products;
    }

    @Override
    public Product createProduct(ProductRequest request) {
        if(!productRepository.findByName(request.getName()).isEmpty()){
            throw new ApiException(HttpStatus.BAD_REQUEST, "product already exist");
        }
        categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "category not found"));
        brandRepository.findById(request.getBrandId()).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "brand not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategoryId(request.getCategoryId());
        product.setBrandId(request.getBrandId());
        return productRepository.save(product);
        }

    @Override
    public ApiResponse<Object> updateProduct(Long id, ProductRequest request) {
        if(!productRepository.findByName(request.getName()).isEmpty()){
            throw new ApiException(HttpStatus.BAD_REQUEST, "product already exist");
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "product not found"));
        if(product.getBrandId().equals(request.getBrandId()) && product.getCategoryId().equals(request.getCategoryId())){
            product.setName(request.getName());
            product.setPrice(request.getPrice());
            productRepository.save(product);
        }
        categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "category not found"));
        brandRepository.findById(request.getBrandId()).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "brand not found"));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategoryId(request.getCategoryId());
        product.setBrandId(request.getBrandId());
        productRepository.save(product);

        return ApiResponse.builder()
                .statusCode(200)
                .message("UPDATED")
                .data(null)
                .build();
    }

    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "product not found"));
        existingProduct.setIsActive(false);
        productRepository.save(existingProduct);
    }
}
