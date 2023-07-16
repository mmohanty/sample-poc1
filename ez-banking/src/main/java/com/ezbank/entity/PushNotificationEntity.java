package com.ezbank.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PUSH_NOTIFICATION")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "status")
    private String status;

    @Column(name = "username")
    private String username;
}
