package com.mybank.banking.service.Impl;

import com.mybank.banking.dto.AccountDto;
import com.mybank.banking.mapper.AccountMapper;
import com.mybank.banking.entity.Account;
import com.mybank.banking.repository.AccountRepository;
import com.mybank.banking.service.AccountService;
import org.springframework.stereotype.Service;
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
      Account account= accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account= accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account does not exist"));
     double total = account.getBalance()+ amount;
    account.setBalance(total);
    Account savedAccount=accountRepository.save(account);

    return AccountMapper.mapToAccountDto(savedAccount);
}

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account= accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account does not exist"));
        if(account.getBalance()<amount){
        throw new RuntimeException("Insufficient amount");}
        double total = account.getBalance()- amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);   }
    @Override
    public void transferFunds(Long sourceAccountId, Long targetAccountId, double amount) {

        Account sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new RuntimeException("Source account does not exist"));
        Account targetAccount = accountRepository.findById(targetAccountId)
                .orElseThrow(() -> new RuntimeException("Target account does not exist"));


        if (sourceAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in source account for transfer");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);


        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);
    }
    @Override
    public void closeAccount(Long id) {
        // Retrieve account from the database
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        // Delete the account
        accountRepository.delete(account);
    }
    @Override
    public AccountDto updateAccountInformation(Long id, AccountDto updatedAccountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        account.setAccountHolderName(updatedAccountDto.getAccountHolderName());


        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }
    @Override
    public double getAccountBalance(Long id) {
        // Retrieve account from the database
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        // Return the current balance of the account
        return account.getBalance();
    }
}