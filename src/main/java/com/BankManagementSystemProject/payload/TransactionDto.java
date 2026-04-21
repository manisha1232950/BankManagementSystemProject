package com.BankManagementSystemProject.payload;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {

    private Integer transactionId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotBlank(message = "Transaction type is required")
    private String type; // DEPOSIT / WITHDRAW / TRANSFER

    private LocalDateTime date;
}