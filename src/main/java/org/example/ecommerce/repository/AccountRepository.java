package org.example.ecommerce.repository;

import org.example.ecommerce.dto.response.account.AccountResponse;
import org.example.ecommerce.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query(value = """
        SELECT 
            a.id, 
            a.username, 
            a.email, 
            a.isActive, 
            r.name
        FROM Account a
        LEFT JOIN AccountRole ar ON a.id = ar.accountId
        LEFT JOIN Role r ON ar.roleId = r.id
        WHERE (:username IS NULL OR a.username LIKE %:username%)
          AND a.isActive = true
          AND (:email IS NULL OR a.email LIKE %:email%)
          AND (:roleIds IS NULL OR r.id IN :roleIds)
        """,
            countQuery = """
        SELECT COUNT(DISTINCT a.id)
        FROM Account a
        LEFT JOIN AccountRole ar ON a.id = ar.accountId
        LEFT JOIN Role r ON ar.roleId = r.id
        WHERE (:username IS NULL OR a.username LIKE %:username%)
          AND a.isActive = true
          AND (:email IS NULL OR a.email LIKE %:email%)
          AND (:roleIds IS NULL OR r.id IN :roleIds)
        """
    )
    Page<Object[]> searchAccountsWithRoles(@Param("username") String username,
                                           @Param("email") String email,
                                           @Param("roleIds") List<Long> roleIds,
                                           Pageable pageable);

    @Query("""
    SELECT 
            a.id, 
            a.username, 
            a.email, 
            a.isActive, 
            r.name
    FROM Account a
    LEFT JOIN AccountRole ar ON a.id = ar.accountId
    LEFT JOIN Role r ON ar.roleId = r.id
    WHERE a.id = :id
    AND a.isActive = true
""")
    List<Object[]> getAccounts(@Param("id") Long id);
}
