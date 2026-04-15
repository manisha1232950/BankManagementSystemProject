package com.BankManagementSystemProject.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

 private int id;
        private String UserName;
        private int Password;
        private String Gmail;

    }

