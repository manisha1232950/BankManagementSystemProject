package com.BankManagementSystemProject.repository;

import com.BankManagementSystemProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User>  findByUsername(String username);
 //   Optional<User> findByEmail(String email);
//SELECT * FROM user WHERE email = ? behind the scene

    }

