package com.ezbank.service;

import com.ezbank.model.response.MerchantResponse;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MerchantService {

    private Map<String, MerchantResponse> map = new ConcurrentHashMap<>();

    public MerchantService(){
        map.put("M-1", MerchantResponse.builder().id("M-1").name("TransDepo").build());
        map.put("M-2", MerchantResponse.builder().id("M-2").name("PerFit").build());
    }

    public MerchantResponse getMerchant(String merchantId){
        return map.get(merchantId);
    }
}
