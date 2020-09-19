package com.etransact.accountmanagment.serviceImpl;

import com.etransact.accountmanagment.dao.TransactionRepository;
import com.etransact.accountmanagment.domain.entity.Account;
import com.etransact.accountmanagment.domain.entity.Transaction;
import com.etransact.accountmanagment.domain.enumeration.TransactionTypeConstant;
import com.etransact.accountmanagment.service.TransactionService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Named
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Inject
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public synchronized Transaction doTransaction(Account from, BigInteger amount, TransactionTypeConstant transactionType, String description) {


        Random r = new Random();
        int low = 0;
        int high = 100000;
        int result = r.nextInt(high - low) + low;
        String transactionReference = String.format(Locale.ENGLISH, "%04d", result);

        Transaction.TransactionBuilder builder = Transaction.builder()
                .account(from)
                .description(description)
                .reference(transactionReference);

        return transactionRepository.findTopByOrderByLastModifiedDescAccount(from).map(transaction -> {
            if (transactionType.equals(TransactionTypeConstant.CREDIT)) {
                builder.balance(transaction.getBalance().add(amount)).credit(amount);
            } else {
                builder.balance(transaction.getBalance().subtract(amount)).debit(amount);
            }
            Transaction tran = builder.build();
            return transactionRepository.save(tran);
        }).orElseGet(() -> {
            builder.balance(amount);
            if (transactionType.equals(TransactionTypeConstant.CREDIT)) {
                builder.credit(amount);
            } else {
                builder.debit(amount);
            }
            Transaction tran = builder.build();
            return transactionRepository.save(tran);
        });

    }
}
