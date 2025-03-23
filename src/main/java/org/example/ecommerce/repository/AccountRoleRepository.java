package org.example.ecommerce.repository;

import org.example.ecommerce.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    Optional<AccountRole> findByRoleId(Long roleId);
    Optional<AccountRole> findByAccountId(Long accountId);
    void deleteByAccountId(Long accountId);
}
