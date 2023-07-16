package com.ezbank.model.response;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponse {

    private String id;
    private String message;
    private String receiverName;
    private BigDecimal amount;
    private Status status;
    private String location;
    private String time;
}
