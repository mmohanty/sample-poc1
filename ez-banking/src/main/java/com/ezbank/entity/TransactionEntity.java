package com.ezbank.entity;

import com.ezbank.model.response.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TRANSACTION")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "message")
    private String message;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private String status;

    @Column(name = "time")
    private String time;

    @Column(name = "location")
    private String location;
}
