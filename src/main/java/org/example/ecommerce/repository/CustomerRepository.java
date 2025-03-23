package org.example.ecommerce.repository;

import org.example.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByAccountId(Long id);
    boolean existsByPhone(String phone);
}
