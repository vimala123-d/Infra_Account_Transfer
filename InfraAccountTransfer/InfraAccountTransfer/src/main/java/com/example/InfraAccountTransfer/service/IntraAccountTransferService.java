package com.example.InfraAccountTransfer.service;


import com.example.InfraAccountTransfer.dto.IntraAccountTransferRequest;
import com.example.InfraAccountTransfer.model.*;
import com.example.InfraAccountTransfer.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class IntraAccountTransferService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public IntraAccountTransferService(UserRepository userRepository,
                                       AccountRepository accountRepository,
                                       TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public String transfer(IntraAccountTransferRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account fromAccount = accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        // Ownership validation
        if (!fromAccount.getUser().getUserId().equals(user.getUserId()) ||
                !toAccount.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Accounts do not belong to the same user");
        }

        if (!"ACTIVE".equalsIgnoreCase(fromAccount.getStatus()) ||
                !"ACTIVE".equalsIgnoreCase(toAccount.getStatus())) {
            throw new RuntimeException("One or both accounts are not active");
        }

        BigDecimal amount = request.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be greater than zero");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Debit & Credit
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Log transactions
        Transaction debitTransaction = new Transaction();
        debitTransaction.setAccount(fromAccount);
        debitTransaction.setAmount(amount);
        debitTransaction.setTransactionType("DEBIT");
        debitTransaction.setTransactionDate(LocalDateTime.now());
        debitTransaction.setDescription("Intra-account transfer to " + toAccount.getAccountNumber());

        Transaction creditTransaction = new Transaction();
        creditTransaction.setAccount(toAccount);
        creditTransaction.setAmount(amount);
        creditTransaction.setTransactionType("CREDIT");
        creditTransaction.setTransactionDate(LocalDateTime.now());
        creditTransaction.setDescription("Intra-account transfer from " + fromAccount.getAccountNumber());

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        return UUID.randomUUID().toString();
    }
}

