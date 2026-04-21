package com.BankManagementSystemProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    private Double amount;

    private String type; // DEPOSIT / WITHDRAW / TRANSFER

    private LocalDateTime date;

    @ManyToOne
    private Account account;
}
