package com.bankmgmt.app.service;

import ch.qos.logback.core.model.NamedModel;
import com.bankmgmt.app.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BankService {

    private List<Account> accounts = new ArrayList<>();
    private Integer currentId = 1;


    // TODO: Method to Create a new Account

    public ResponseEntity<Object> createBankAccount(Account account) {
        if(account.getAccountHolderName() == null || account.getAccountHolderName().trim().isEmpty()){
            return new ResponseEntity<>("AccountHolderName should not be blank",HttpStatus.BAD_REQUEST);
        }
        if(account.getEmail()==null || account.getEmail().isBlank()){
            return new ResponseEntity<>("Email should not be blank",HttpStatus.BAD_REQUEST);
        }
       if( accounts.stream().anyMatch(obj->obj.getEmail().equals(account.getEmail()))){
           return new ResponseEntity<>("Email already Exist",HttpStatus.BAD_REQUEST);
       }
        account.setId(currentId);
        currentId++;
        accounts.add(account);
        return ResponseEntity.ok(account);
    }

    // TODO: Method to Get All Accounts
    public ResponseEntity<List<Account>> getAllAccounts() {
        if(accounts.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(accounts);
    }

    // TODO: Method to Get Account by ID
    public ResponseEntity<Account> getAccountById(Integer id) {

       Account account =  accounts.stream().filter(obj->obj.getId().equals(id)).findFirst().orElse(null);
       if(account == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       return ResponseEntity.ok(account);
    }


    // TODO: Method to Deposit Money to the specified account id

    public ResponseEntity<Account> depositToIdAndGetBalance(Integer id, Account account) {

       Account accountFromDb =  getAccount(id);

        if(accountFromDb != null){
            accountFromDb.setBalance(accountFromDb.getBalance()+ account.getAmount());
            return ResponseEntity.ok(accountFromDb);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private Account getAccount(Integer id) {
        return  accounts.stream().filter(obj->obj.getId().equals(id)).findFirst().orElse(null);
    }


    // TODO: Method to Withdraw Money from the specified account id
    public ResponseEntity<Account> wihdrawIdAndGetBalance(Integer id, Account account) {

        Account accountFromDb = getAccount(id);
        if(accountFromDb != null){
            if(accountFromDb.getBalance()<account.getAmount()){
                new ResponseEntity<>("Insufficient Funds",HttpStatus.BAD_REQUEST);
            }else{
                accountFromDb.setBalance(accountFromDb.getBalance()-account.getAmount());
                return ResponseEntity.ok(accountFromDb);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
    // TODO: Method to Transfer Money from one account to another
    public ResponseEntity<Object> transferMoneyFromIdToId(Integer fromId, Integer toId, Double amount) {
        Account account1 = getAccount(fromId);
        Account account2 = getAccount(toId);
        if(account1 == null || account2 == null){
            return new ResponseEntity<>("Invalid Accounts",HttpStatus.BAD_REQUEST);
        }
        else if(account1.getBalance()< amount){
            return new ResponseEntity<>("insuuficient funds",HttpStatus.BAD_REQUEST);
        }

        account1.setBalance(account1.getBalance()-amount);
        account2.setBalance(account2.getBalance()+amount);
        ;
       return ResponseEntity.ok(List.of(account1,account2));
    }



    // TODO: Method to Delete Account given a account id

    public ResponseEntity<Object> deleteAccountById(Integer id) {
       Account account = getAccount(id);
        if(account == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        accounts.remove(account);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
