package com.BankManagementSystemProject.repository;

import com.BankManagementSystemProject.entity.Account;
import com.BankManagementSystemProject.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

    List<Transaction> findByAccount(Account account);

}
