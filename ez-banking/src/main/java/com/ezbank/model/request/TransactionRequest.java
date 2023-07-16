package com.ezbank.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    @NotNull
    private String cardHolderName;

    @NotNull
    private String cardNumber;

    @NotNull
    private String receiverName;

    private String receiverAccount;

    @NotNull
    @JsonProperty("ipLocation")
    private String ip;

    private String zipCode;

    @NotNull
    private String cvv;

    @NotNull
    @JsonProperty("month")
    private String expiryMonth;

    @NotNull
    @JsonProperty("year")
    private String expiryYear;
    private BigDecimal amount;

    @JsonIgnore
    private String time;
}
