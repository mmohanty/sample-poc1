package com.ezbank.controller;

import com.ezbank.entity.PushNotificationEntity;
import com.ezbank.model.request.PushNotificationRequest;
import com.ezbank.model.response.PushNotificationMessage;
import com.ezbank.service.OTPService;
import com.ezbank.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin
public class NotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private OTPService otpService;

    @GetMapping(path = "/pn/{username}")
    public ResponseEntity<PushNotificationMessage> getPushNotification(@PathVariable @NotNull String username){
       return   pushNotificationService.getPushNotification(username);
    }

    @GetMapping(path = "/otp/{username}")
    public ResponseEntity<String> getOTP(@PathVariable @NotNull String username){
        return ResponseEntity.ok(otpService.getOtp(username));
    }


    @PostMapping(path = "/pn/authorize")
    public ResponseEntity<String> authorizePushNotification(PushNotificationRequest pushNotificationRequest){
        return pushNotificationService.authorisePushNotification(pushNotificationRequest);
    }

    @GetMapping(path = "/pn/user/{username}/status/{status}")
    public ResponseEntity<List<PushNotificationEntity>> getPushNotificationByStatus(@PathVariable String username, @PathVariable String status){
        return pushNotificationService.getPushNotificationByStatus(username, status);
    }
}
