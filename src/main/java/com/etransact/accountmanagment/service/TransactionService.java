package com.etransact.accountmanagment.service;

import com.etransact.accountmanagment.domain.entity.Account;
import com.etransact.accountmanagment.domain.entity.Transaction;
import com.etransact.accountmanagment.domain.enumeration.TransactionTypeConstant;

import java.math.BigInteger;

public interface TransactionService {
    Transaction doTransaction(Account account, BigInteger amount, TransactionTypeConstant transactionType, String description);
}
