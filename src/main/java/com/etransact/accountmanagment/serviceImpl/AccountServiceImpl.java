package com.etransact.accountmanagment.serviceImpl;


import com.etransact.accountmanagment.dao.AccountRepository;
import com.etransact.accountmanagment.data.AccountRequest;
import com.etransact.accountmanagment.domain.entity.Account;
import com.etransact.accountmanagment.domain.enumeration.GenericStatusConstant;
import com.etransact.accountmanagment.domain.enumeration.TransactionTypeConstant;
import com.etransact.accountmanagment.exception.AccountBalanceException;
import com.etransact.accountmanagment.service.AccountService;
import com.etransact.accountmanagment.service.TransactionService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Named
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    @Override
    @Transactional
    public Account createAccount(AccountRequest account) {
        Random r = new Random();
        int low = 567;
        int high = 100000;
        int result = r.nextInt(high - low) + low;
        String firstSet = String.format(Locale.ENGLISH, "%04d", result);
        result = r.nextInt(high - low) + low;
        String sendIt = String.format(Locale.ENGLISH, "%05d", result);

        Account newAccount = new Account(account.getAccountName(), firstSet.concat(sendIt));
        accountRepository.save(newAccount);
        newAccount.setBalanceInMinorUnit(newAccount.getBalanceInMinorUnit().add(account.getOpenBalance()));
        accountRepository.save(newAccount);
        transactionService.doTransaction(newAccount, account.getOpenBalance(), TransactionTypeConstant.CREDIT, String.format("Credited account with %d", account.getOpenBalance()));
        return newAccount;

    }


    @Override
    public synchronized void transfer(Account from, Account to, BigInteger amount) {
        if (from.getBalanceInMinorUnit().compareTo(amount) < 0) {
            throw new AccountBalanceException("Account balance is less than amount");
        }
        from.setBalanceInMinorUnit(from.getBalanceInMinorUnit().subtract(amount));
        accountRepository.save(from);
        to.setBalanceInMinorUnit(to.getBalanceInMinorUnit().add(amount));
        accountRepository.save(to);
        transactionService.doTransaction(from, amount, TransactionTypeConstant.DEBIT, String.format("Account debited with %d to %s", amount, from.getName()));
        transactionService.doTransaction(to, amount, TransactionTypeConstant.CREDIT, String.format("Account credited with %d from %s", amount, from.getName()));
    }

    @Override
    @Transactional
    public void closeAccount(Account account) {
        account.setStatus(GenericStatusConstant.IN_ACTIVE);
        accountRepository.save(account);
    }
}
