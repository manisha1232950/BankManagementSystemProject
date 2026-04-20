package com.BankManagementSystemProject.service;

import com.BankManagementSystemProject.payload.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto dto);

    AccountDto updateAccount(AccountDto dto, Integer id);

    AccountDto getAccountById(Integer id);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Integer id);

}
