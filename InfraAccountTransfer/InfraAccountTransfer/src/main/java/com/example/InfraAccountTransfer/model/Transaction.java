package com.example.InfraAccountTransfer.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "transaction_type")
    private String transactionType; // DEBIT or CREDIT

    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // Getters and Setters
}

