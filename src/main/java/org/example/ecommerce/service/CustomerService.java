package org.example.ecommerce.service;


import org.example.ecommerce.dto.request.customer.CustomerRequest;
import org.example.ecommerce.dto.response.customer.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(Long accountId);
    CustomerResponse updateCustomer(Long id, CustomerRequest request);
    void deleteCustomer(Long id);
    CustomerResponse getCustomerById(Long id);
    List<CustomerResponse> getListCustomers();
    boolean checkCustomerExist(Long id);
}
