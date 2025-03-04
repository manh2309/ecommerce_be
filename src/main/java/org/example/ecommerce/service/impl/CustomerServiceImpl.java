package org.example.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dto.LoginRequest;
import org.example.ecommerce.entity.Customer;
import org.example.ecommerce.repository.CustomerRepository;
import org.example.ecommerce.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

//    @Override
//    public Customer login(LoginRequest loginRequest) {
//        Customer customer = customerRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
//        return customer;
//    }
}
