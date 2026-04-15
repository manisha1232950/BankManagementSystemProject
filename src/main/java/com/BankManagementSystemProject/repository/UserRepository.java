package com.BankManagementSystemProject.repository;

import com.BankManagementSystemProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    }

