package com.bankmgmt.app.rest;


import com.bankmgmt.app.entity.Account;
import com.bankmgmt.app.service.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Make this class a Rest Controller
@RestController
@RequestMapping("/accounts")
public class BankController {

    // TODO Autowired the BankService class
    @Autowired
    private BankService bankService;

    // TODO: API to Create a new account
    @PostMapping()
    public ResponseEntity<Object> createBankAccount(@Valid @RequestBody Account account){
        return bankService.createBankAccount(account);

    }
    // TODO: API to Get all accounts

    @GetMapping()
    public ResponseEntity<List<Account>> getAllCounts(){
        return bankService.getAllAccounts();
    }
    // TODO: API to Get an account by ID

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id){
        return bankService.getAccountById(id);
    }
    // TODO: API to Deposit money

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> depositToId(@PathVariable Integer id,@Valid@RequestBody Account account){
        return bankService.depositToIdAndGetBalance(id,account);
    }
    // TODO: API to Withdraw money

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withDrawFromId(@PathVariable Integer id,@Valid@RequestBody Account account){
        return bankService.wihdrawIdAndGetBalance(id,account);
    }
    // TODO: API to Transfer money between accounts

    @PostMapping("/transfer")
    public ResponseEntity<Object> withDrawFromId(@RequestParam("fromId")Integer fromId,@RequestParam("toId")Integer toId,@RequestParam("amount")Double amount){
        return bankService.transferMoneyFromIdToId(fromId,toId,amount);
    }
    // TODO: API to Delete an account
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccountById(@PathVariable Integer id){
        return bankService.deleteAccountById(id);
    }
    
    
}
