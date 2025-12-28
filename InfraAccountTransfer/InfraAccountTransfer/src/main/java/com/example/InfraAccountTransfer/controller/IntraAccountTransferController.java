package com.example.InfraAccountTransfer.controller;


import com.example.InfraAccountTransfer.dto.IntraAccountTransferRequest;
import com.example.InfraAccountTransfer.service.IntraAccountTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfer")
public class IntraAccountTransferController {

    private final IntraAccountTransferService service;

    public IntraAccountTransferController(IntraAccountTransferService service) {
        this.service = service;
    }

    @PostMapping("/intra")
    public ResponseEntity<?> intraAccountTransfer(@RequestBody IntraAccountTransferRequest request) {
        String txnRef = service.transfer(request);
        return ResponseEntity.ok("Transfer successful. Transaction Reference: " + txnRef);
    }
}
