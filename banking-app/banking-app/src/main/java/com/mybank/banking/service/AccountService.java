package com.mybank.banking.service;

import com.mybank.banking.dto.AccountDto;


public interface AccountService {
    AccountDto createAccount(AccountDto accountDto );
    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id, double amount);

    // Distinct functions other then video

    //1.
    void transferFunds(Long sourceAccountId, Long targetAccountId, double amount);

    //2.
    void closeAccount(Long id);

    //3.
    AccountDto updateAccountInformation(Long id, AccountDto updatedAccountDto);

    //4.
    double getAccountBalance(Long id);

    }
