package com.example.InfraAccountTransfer.exception;

public class AccountBlockedException extends RuntimeException {
    public AccountBlockedException(String msg) {
        super(msg);
    }
}
