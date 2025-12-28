package com.example.InfraAccountTransfer.exception;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException(String s) {
        super(s);
    }
}
