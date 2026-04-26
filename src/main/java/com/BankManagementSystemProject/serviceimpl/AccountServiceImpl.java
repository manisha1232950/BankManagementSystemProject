package com.BankManagementSystemProject.serviceimpl;

import com.BankManagementSystemProject.entity.Account;
import com.BankManagementSystemProject.entity.User;
import com.BankManagementSystemProject.exceptionhandling.ResourceNotFoundException;
import com.BankManagementSystemProject.payload.AccountDto;
import com.BankManagementSystemProject.payload.UserDto;
import com.BankManagementSystemProject.repository.AccountRepository;
import com.BankManagementSystemProject.repository.UserRepository;
import com.BankManagementSystemProject.service.AccountService;
import jakarta.persistence.ManyToOne;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @ManyToOne

    //post method -create account
    @Override
    public AccountDto createAccount(AccountDto accountDto) {

        // Example validation
        if(accountDto.getBalance() < 0){
            throw new RuntimeException("Balance cannot be negative");
        }

        // DTO → Entity
        Account account = this.modelMapper.map(accountDto, Account.class);
        // Save
        Account addedAccount = this.accountRepo.save(account);
// Return
        return this.modelMapper.map(addedAccount, AccountDto.class);
    }
//updaate account
@Override
public AccountDto updateAccount(AccountDto dto, Integer id) {

    Account account = this.accountRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));

    account.setAccountNumber(dto.getAccountNumber());
    account.setBalance(dto.getBalance());
    account.setAccountType(dto.getAccountType());

    User user = this.userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));

    account.setUser(user);

    Account updatedAccount = this.accountRepo.save(account);

    return this.modelMapper.map(updatedAccount, AccountDto.class);
}
//  getAccountById
    @Override
    public AccountDto getAccountById(Integer id) {

        Account account = this.accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));

        return this.modelMapper.map(account, AccountDto.class);
    }

//getAllAccounts

    @Override
    public List<AccountDto> getAllAccounts() {

        return this.accountRepo.findAll().stream()
                .map(acc -> this.modelMapper.map(acc, AccountDto.class))
                .toList();
    }

    //Delete account
    @Override
    public void deleteAccount(Integer id) {

        Account account = this.accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));

        this.accountRepo.delete(account);
    }
}