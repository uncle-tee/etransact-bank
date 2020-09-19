package com.etransact.accountmanagment.dao;

import com.etransact.accountmanagment.domain.entity.Account;
import com.etransact.accountmanagment.domain.entity.Transaction;
import com.etransact.accountmanagment.domain.enumeration.GenericStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.account = :account order by t.lastModified")
    Optional<Transaction> findTopByOrderByLastModifiedDescAccount(Account account);

    List<Transaction> findAllByAccountAndStatus(Account account, GenericStatusConstant statusConstante);


}
