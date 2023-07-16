package com.ezbank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class OTPService {

    private Map<String, String> map = new ConcurrentHashMap<>();

    public String getOtp(String username) {
        return map.get(username);
    }

    public void removeOTP(String username) {
        map.remove(username);
    }

    public void createOTP(String username) {
        String otp = getRandomNumberString();
        log.info("User {} otp is {}", username, otp);
        map.put(username, otp);
    }

    private String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        SecureRandom rnd = new SecureRandom();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
