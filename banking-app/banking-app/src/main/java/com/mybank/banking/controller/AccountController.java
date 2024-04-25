package com.mybank.banking.controller;

import com.mybank.banking.dto.AccountDto;
import com.mybank.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    // Add Account API

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);

    }
    //Get account Rest API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){

        AccountDto accountDto= accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);

    }

    //Deposit REST API

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request){
        Double amount= request.get("amount");
       AccountDto accountDto=  accountService.deposit(id, amount);
       return ResponseEntity.ok(accountDto);

    }
// Withdraw REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request){
        Double amount= request.get("amount");
        AccountDto accountDto=  accountService.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);

    }
    //Fund Transfer REST API
    @PutMapping("/{sourceId}/transfer/{targetId}")
    public ResponseEntity<Void> transferFunds(@PathVariable Long sourceId,
                                              @PathVariable Long targetId,
                                              @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        accountService.transferFunds(sourceId, targetId, amount);
        return ResponseEntity.noContent().build();
    }
    // Close account REST API
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> closeAccount(@PathVariable Long id) {
        accountService.closeAccount(id);
        return ResponseEntity.noContent().build();
    }
    // Update account information REST API
    @PutMapping("/{id}/update")
    public ResponseEntity<AccountDto> updateAccountInformation(@PathVariable Long id,
                                                               @RequestBody AccountDto updatedAccountDto) {
        AccountDto accountDto = accountService.updateAccountInformation(id, updatedAccountDto);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable Long id) {
        double balance = accountService.getAccountBalance(id);
        return ResponseEntity.ok(balance);
    }
}
