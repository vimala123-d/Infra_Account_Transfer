package com.example.InfraAccountTransfer.exception;

public class AccountsNotBelongToSameUSerException extends RuntimeException {
    public AccountsNotBelongToSameUSerException(String msg) {
        super(msg);
    }
}
