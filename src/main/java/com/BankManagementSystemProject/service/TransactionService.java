package com.BankManagementSystemProject.service;

import com.BankManagementSystemProject.payload.TransactionDto;

import java.util.List;

public interface TransactionService {

    TransactionDto deposit(Integer accountId, Double amount);

    TransactionDto withdraw(Integer accountId, Double amount);

    void transfer(Integer fromAccountId, Integer toAccountId, Double amount);

    List<TransactionDto> getTransactionsByAccount(Integer accountId);
}
