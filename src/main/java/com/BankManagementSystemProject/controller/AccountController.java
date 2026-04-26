package com.BankManagementSystemProject.controller;

import com.BankManagementSystemProject.payload.AccountDto;
import com.BankManagementSystemProject.payload.UserDto;
import com.BankManagementSystemProject.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
   private AccountService accountService;

    // Create Account
    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto dto) {

        AccountDto createdAccount = this.accountService.createAccount(dto);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

//PUT (Update)
    @PutMapping("/accountupdate/{accountId}")
    public ResponseEntity<AccountDto> updateAccount( @Valid
            @RequestBody AccountDto dto,
            @PathVariable Integer accountId) {

        AccountDto updated = this.accountService.updateAccount(dto, accountId);

        return ResponseEntity.ok(updated);
    }

// get account by Id
@GetMapping("/{accountId}")
public ResponseEntity<AccountDto> getAccountById(@PathVariable Integer accountId) {

   AccountDto accountDto = this.accountService.getAccountById(accountId);

    return ResponseEntity.ok(accountDto);
}

    //GET list
    @GetMapping("/listaccounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {

        List<AccountDto> accounts = this.accountService.getAllAccounts();

        return ResponseEntity.ok(accounts);
    }
    //delete account

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer accountId) {

        this.accountService.deleteAccount(accountId);

        return ResponseEntity.ok("Account deleted successfully");
    }

}
