package com.ezbank.controller;

import com.ezbank.model.request.TransactionAuthenticationRequest;
import com.ezbank.model.request.TransactionRequest;
import com.ezbank.model.response.TransactionHistoryResponse;
import com.ezbank.model.response.TransactionResponse;
import com.ezbank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/transaction")
//@CrossOrigin(origins="http://localhost:4200")
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "/initiate")
    public ResponseEntity<TransactionResponse> initiate(@RequestBody TransactionRequest request){
        return transactionService.initiate(request);
    }

    @PostMapping(path = "/authorize")
    public ResponseEntity<TransactionResponse> authorize(@RequestBody TransactionAuthenticationRequest request){
        return transactionService.authorize(request);
    }

    @GetMapping(path = "/{username}")
    public List<TransactionHistoryResponse> getAllTransactions(@PathVariable @NotNull String username){
        return transactionService.getAllTransactions(username);
    }
}
