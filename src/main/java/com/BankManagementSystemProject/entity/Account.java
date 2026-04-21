package com.BankManagementSystemProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int accountId;
     private String accountNumber;
     private Double balance;

    private String accountType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
