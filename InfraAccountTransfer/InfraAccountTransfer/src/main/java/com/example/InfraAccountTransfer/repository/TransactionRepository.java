package com.example.InfraAccountTransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InfraAccountTransfer.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

