package org.example.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.common.dto.ApiResponse;
import org.example.ecommerce.dto.request.customer.CustomerRequest;
import org.example.ecommerce.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PutMapping("/update/{id}")
    public ApiResponse<Object> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customer) {
        return ApiResponse.success(HttpStatus.OK, customerService.updateCustomer(id, customer), "update customer thành công");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Object> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ApiResponse.success(HttpStatus.NO_CONTENT, null, "deleted customer");
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<Object> getCustomer(@PathVariable Long id) {
        return ApiResponse.success(HttpStatus.OK, customerService.getCustomerById(id), "Data success");
    }

    @GetMapping("")
    public ApiResponse<Object> getAllCustomer() {
        return ApiResponse.success(HttpStatus.OK, customerService.getListCustomers(), "Data success");
    }
}
