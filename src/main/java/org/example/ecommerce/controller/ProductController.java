package org.example.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.common.dto.ApiResponse;
import org.example.ecommerce.dto.ProductRequest;
import org.example.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}")
    public ApiResponse<?> getProductById(@PathVariable Long productId) {
        return ApiResponse.successOf(HttpStatus.OK,"Data successfully",productService.getProductById(productId));
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<?> getProductsByCategory(@PathVariable Long categoryId) {
        return ApiResponse.successOf(HttpStatus.OK, "Data successfully", productService.getProductByCategory(categoryId));
    }

    @PostMapping
    public ApiResponse<?> addProduct(@RequestBody ProductRequest product) {
        return ApiResponse.successOf(HttpStatus.CREATED,"Tao product thanh cong", productService.createProduct(product));
    }

    @PutMapping("/{id}")
    public ApiResponse<Object> updateProduct(@PathVariable Long id, @RequestBody ProductRequest product) {
        return ApiResponse.success(HttpStatus.OK, productService.updateProduct(id, product), "Cap nhat product thanh cong");

    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
