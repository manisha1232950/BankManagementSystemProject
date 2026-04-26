package com.BankManagementSystemProject.controller;

import com.BankManagementSystemProject.payload.TransactionDto;
import com.BankManagementSystemProject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransationController {

    @Autowired
    private TransactionService txnService;

    @PostMapping("/deposit/{accountId}")
    public TransactionDto deposit(@PathVariable Integer accountId,
                                  @RequestBody Double amount) {
        return txnService.deposit(accountId, amount);
    }

//========================================================================

    @PostMapping("/withdraw/{accountId}")
    public TransactionDto withdraw(@PathVariable Integer accountId,
                                   @RequestBody TransactionDto dto) {

        return txnService.deposit(accountId, dto.getAmount());
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Integer from,
                           @RequestParam Integer to,
                           @RequestParam Double amount) {
        txnService.transfer(from, to, amount);
        return "Transfer successful";
    }

    @GetMapping("/{accountId}")
    public List<TransactionDto> getHistory(@PathVariable Integer accountId) {
        return txnService.getTransactionsByAccount(accountId);
    }

}
