package com.BankManagementSystemProject.entity;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int accountId;
     private String accountNumber;
     private Double balance;

    private String accountType;

   // @ManyToOne
    //private User user;
}
