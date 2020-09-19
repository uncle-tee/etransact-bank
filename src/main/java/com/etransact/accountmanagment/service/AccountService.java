package com.etransact.accountmanagment.service;

import com.etransact.accountmanagment.data.AccountRequest;
import com.etransact.accountmanagment.domain.entity.Account;

import java.math.BigInteger;

public interface AccountService {
    Account createAccount(AccountRequest account);

    void transfer(Account from, Account to, BigInteger amount);

    void closeAccount(Account account);
}
