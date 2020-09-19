package com.etransact.accountmanagment.dao;

import com.etransact.accountmanagment.domain.entity.Account;
import com.etransact.accountmanagment.domain.enumeration.GenericStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumberAndStatus(String number, GenericStatusConstant status);

    List<Account> findByNumberInAndStatus(List<String> accountNumbers, GenericStatusConstant status);
}
