package com.ezbank.controller;

import com.ezbank.model.response.MerchantResponse;
import com.ezbank.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant")
@CrossOrigin
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<MerchantResponse> getMerchantDetails(@PathVariable String id){
        return ResponseEntity.ok(merchantService.getMerchant(id));
    }
}
