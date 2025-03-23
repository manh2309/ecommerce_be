package org.example.ecommerce.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dto.request.customer.CustomerRequest;
import org.example.ecommerce.dto.response.customer.CustomerResponse;
import org.example.ecommerce.entity.Customer;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.repository.AccountRepository;
import org.example.ecommerce.repository.CustomerRepository;
import org.example.ecommerce.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public CustomerResponse createCustomer(Long accountId) {
        accountRepository.findById(accountId).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "account not found"));

        Customer customer = Customer.builder().accountId(accountId).isActive(true).build();
        customerRepository.save(customer);
        return CustomerResponse.builder().accountId(customer.getAccountId()).build();
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "customer not found"));

        if (customerRepository.existsByPhone(request.getPhone()) && !customer.getPhone().equals(request.getPhone())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Phone number already exists");
        }
        customer.setFullName(request.getFullName());
        customer.setGender(request.getGender());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        if (request.getDateOfBirth() != null) {
            customer.setDateOfBirth(LocalDate.parse(request.getDateOfBirth())); // <<<< Parse chuỗi sang LocalDate
        }
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerResponse.builder()
                .id(savedCustomer.getId())
                .accountId(savedCustomer.getAccountId())
                .dateOfBirth(savedCustomer.getDateOfBirth())
                .phone(savedCustomer.getPhone())
                .fullName(savedCustomer.getFullName())
                .gender(savedCustomer.getGender())
                .address(savedCustomer.getAddress())
                .isActive(savedCustomer.getIsActive())
                .build();
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "customer not found"));
        customer.setIsActive(false);
        customerRepository.save(customer);

    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "customer not found"));

        return CustomerResponse.builder()
                .id(customer.getId())
                .dateOfBirth(customer.getDateOfBirth())
                .phone(customer.getPhone())
                .fullName(customer.getFullName())
                .gender(customer.getGender())
                .address(customer.getAddress())
                .isActive(customer.getIsActive())
                .build();
    }

    @Override
    public List<CustomerResponse> getListCustomers() {
        return customerRepository.findAll().stream().map(customer ->
                CustomerResponse.builder()
                        .id(customer.getId())
                        .accountId(customer.getAccountId())
                        .fullName(customer.getFullName())
                        .gender(customer.getGender())
                        .address(customer.getAddress())
                        .phone(customer.getPhone())
                        .dateOfBirth(customer.getDateOfBirth())
                        .isActive(customer.getIsActive())
                        .build()
        ).toList();
    }

    @Override
    public boolean checkCustomerExist(Long id) {
        return customerRepository.existsByAccountId(id);
    }
}
