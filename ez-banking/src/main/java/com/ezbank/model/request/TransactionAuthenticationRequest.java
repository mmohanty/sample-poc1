package com.ezbank.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionAuthenticationRequest {

    private String otp;
    private String transactionId;
    private String cardNumber;
}
