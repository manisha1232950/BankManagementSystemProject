package com.BankManagementSystemProject.controller;

import com.BankManagementSystemProject.payload.TransactionDto;
import com.BankManagementSystemProject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

//=========================================================================
@PostMapping("/deposit/{id}")
public ResponseEntity<?> deposit(
        @RequestBody TransactionDto dto,
        @PathVariable Integer id) {

    TransactionDto result = transactionService.deposit(id, dto.getAmount());

    return new ResponseEntity<>(result, HttpStatus.CREATED);
}


// ===================== WITHDRAW =====================
@PostMapping("/withdraw/{accountId}")
public ResponseEntity<TransactionDto> withdraw(
        @PathVariable Integer accountId,
        @RequestBody TransactionDto dto) {

    TransactionDto result = transactionService.withdraw(accountId, dto.getAmount());

    return ResponseEntity.ok(result);
}

/// ===================== TRANSFER =====================
@PostMapping("/transfer")
public ResponseEntity<String> transfer(
        @RequestParam Integer from,
        @RequestParam Integer to,
        @RequestParam Double amount) {

    transactionService.transfer(from, to, amount);

    return ResponseEntity.ok("Transfer successful");
}

// ===================== HISTORY =====================
@GetMapping("/{accountId}")
public ResponseEntity<List<TransactionDto>> getHistory(
        @PathVariable Integer accountId) {

    List<TransactionDto> list = transactionService.getTransactionsByAccount(accountId);

    return ResponseEntity.ok(list);
}
}


