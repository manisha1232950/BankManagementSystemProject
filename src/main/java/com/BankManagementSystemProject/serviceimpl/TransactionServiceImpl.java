package com.BankManagementSystemProject.serviceimpl;

import com.BankManagementSystemProject.entity.Account;
import com.BankManagementSystemProject.entity.Transaction;
import com.BankManagementSystemProject.exceptionhandling.ResourceNotFoundException;
import com.BankManagementSystemProject.payload.TransactionDto;
import com.BankManagementSystemProject.repository.AccountRepository;
import com.BankManagementSystemProject.repository.TransactionRepository;
import com.BankManagementSystemProject.service.TransactionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private ModelMapper modelMapper;

    // 🔥 Deposit
    @Override
    public TransactionDto deposit(Integer accountId, Double amount) {

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountId", accountId));

        account.setBalance(account.getBalance() + amount);

        Transaction txn = new Transaction();
        txn.setAmount(amount);
        txn.setType("DEPOSIT");
        txn.setDate(LocalDateTime.now());
        txn.setAccount(account);

        Transaction savedTxn = transactionRepo.save(txn);
        accountRepo.save(account);

        return modelMapper.map(savedTxn, TransactionDto.class);
    }

    // 🔥 Withdraw
    @Override
    public TransactionDto withdraw(Integer accountId, Double amount) {

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountId", accountId));

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient Balance");
        }

        account.setBalance(account.getBalance() - amount);

        Transaction txn = new Transaction();
        txn.setAmount(amount);
        txn.setType("WITHDRAW");
        txn.setDate(LocalDateTime.now());
        txn.setAccount(account);

        Transaction savedTxn = transactionRepo.save(txn);
        accountRepo.save(account);

        return modelMapper.map(savedTxn, TransactionDto.class);
    }

    // 🔁 Transfer
    @Override
    @Transactional
    public void transfer(Integer fromId, Integer toId, Double amount) {

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        withdraw(fromId, amount);
        deposit(toId, amount);
    }

    // 📜 Transaction History
    @Override
    public List<TransactionDto> getTransactionsByAccount(Integer accountId) {

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountId", accountId));

        List<Transaction> transactions = transactionRepo.findByAccount(account);

        return transactions.stream()
                .map(txn -> modelMapper.map(txn, TransactionDto.class))
                .toList();
    }
}