package com.example.InfraAccountTransfer.exception;

public class AccountsNotFoundException extends RuntimeException {
    public AccountsNotFoundException(String message) {
        super(message);
    }
}
