package com.example.InfraAccountTransfer.dto;


import lombok.Data;

import java.math.BigDecimal;
@Data
public class IntraAccountTransferRequest {

    private Long userId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;

    // Getters and Setters
}

