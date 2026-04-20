package com.BankManagementSystemProject.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor


public class AccountDto {

    private Integer accountId;

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "\\d{10}", message = "Account number must be 10 digits")
    private String accountNumber;

    @NotNull(message = "Balance is required")
    @Min(value = 0, message = "Balance cannot be negative")
    private Double balance;

    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "SAVINGS|CURRENT", message = "Invalid account type")
    private String accountType;

    @NotNull(message = "User ID is required")
    private Integer userId;
}
