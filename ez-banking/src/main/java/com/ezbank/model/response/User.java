package com.ezbank.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String token;
    private String language;
    private String accountNumber;
    private BigDecimal balance;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardType;
    private String cardCategory;
}
