package com.etransact.accountmanagment.controller;

import com.etransact.accountmanagment.dao.AccountRepository;
import com.etransact.accountmanagment.dao.OffsetBasePageRequest;
import com.etransact.accountmanagment.dao.TransactionRepository;
import com.etransact.accountmanagment.data.AccountRequest;
import com.etransact.accountmanagment.data.PaginatedResponseData;
import com.etransact.accountmanagment.data.ResponseData;
import com.etransact.accountmanagment.data.TransferRequest;
import com.etransact.accountmanagment.domain.entity.Account;
import com.etransact.accountmanagment.domain.entity.Transaction;
import com.etransact.accountmanagment.domain.enumeration.GenericStatusConstant;
import com.etransact.accountmanagment.exception.NotFoundException;
import com.etransact.accountmanagment.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private TransactionRepository transactionRepository;


    public AccountController(AccountService accountService, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    @PostMapping()
    @ResponseBody
    public ResponseData<Account> createAccount(@RequestBody @Valid AccountRequest accountRequest) {
        Account account = accountService.createAccount(accountRequest);
        return new ResponseData<>(account, "Account successfully credited");
    }

    @GetMapping("/{accountNumber}")
    @ResponseBody
    public ResponseData<Account> getAccount(@PathVariable("accountNumber") String accountNumber) {
        Account account = accountRepository.findByNumberAndStatus(accountNumber, GenericStatusConstant.ACTIVE)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Account number %s does not exist", accountNumber))
                );
        return new ResponseData<>(account, "Successful");
    }


    @DeleteMapping("/{accountNumber}")
    public ResponseData<?> closeAccount(@PathVariable("accountNumber") String accountNumber) {
        Account account = accountRepository.findByNumberAndStatus(accountNumber, GenericStatusConstant.ACTIVE)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Account number %s does not exist", accountNumber))
                );
        accountService.closeAccount(account);
        return new ResponseData<>("Account has been closed");
    }

    @PostMapping("/{accountNumber}/transfer")
    public ResponseData<?> transfer(@RequestBody @Valid TransferRequest transferRequest, @PathVariable("accountNumber") String accountNumber) {
        String[] accountNumbers = {accountNumber, transferRequest.getTo()};
        List<String> aNumbers = Arrays.asList(accountNumbers);
        List<Account> accounts = accountRepository.findByNumberInAndStatus(aNumbers, GenericStatusConstant.ACTIVE);
        Account fromAccount = accounts
                .stream()
                .filter(account -> account.getNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException(String.format("Account number %s does not exist", accountNumber))
                );

        Account toAccount = accounts
                .stream()
                .filter(account -> account.getNumber().equals(transferRequest.getTo()))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException(String.format("Account number %s you are transferring to does not exit", transferRequest.getTo()))
                );

        accountService.transfer(fromAccount, toAccount, transferRequest.getAmount());
        return new ResponseData<>("Account Transfer successful");
    }

    @GetMapping("{accountNumber}/transactions")
    public PaginatedResponseData<?> getAccountTransaction(@PathVariable("accountNumber") String accountNumber,
                                                          @RequestParam("limit") Optional<Integer> optionalLimit,
                                                          @RequestParam("offset") Optional<Long> optionalOffset) {
        Integer itemPerPage = optionalLimit.orElse(20);
        Pageable pageable = OffsetBasePageRequest
                .of(optionalOffset.orElse(0L), itemPerPage, Sort.by(Sort.Direction.DESC, "count"));
        Account account = accountRepository.findByNumberAndStatus(accountNumber, GenericStatusConstant.ACTIVE)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Account number %s does not exist", accountNumber))
                );
        List<Transaction> transactions = transactionRepository.findAllByAccountAndStatus(account, GenericStatusConstant.ACTIVE);

        return new PaginatedResponseData<>(transactions.size(), transactions, itemPerPage);
    }
}
