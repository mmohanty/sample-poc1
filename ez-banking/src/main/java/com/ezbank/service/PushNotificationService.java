package com.ezbank.service;

import com.ezbank.utils.Util;
import com.ezbank.entity.PushNotificationEntity;
import com.ezbank.entity.UserEntity;
import com.ezbank.model.request.PushNotificationRequest;
import com.ezbank.model.response.PushNotificationMessage;
import com.ezbank.repository.PushNotificationRepository;
import com.ezbank.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PushNotificationService {

    @Autowired
    private PushNotificationRepository pushNotificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPService otpService;

    @Value("${pn.message}")
    private String message;

    @Value("${date.format}")
    private String dateFormat;

    public ResponseEntity<PushNotificationMessage> getPushNotification(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            return ResponseEntity.badRequest().build();
        }
        List<PushNotificationEntity> entityList = pushNotificationRepository.findByUsernameAndStatusOrderById(username, "UNREAD");
        if(entityList != null && !entityList.isEmpty()){
            PushNotificationEntity entity = entityList.get(0);

            String body = entity.getMessage();
            Gson gson = new Gson();
            PushNotificationMessage response = gson.fromJson(body, PushNotificationMessage.class);
            response.setId(entity.getId().toString());
            entity.setStatus("READ");
            pushNotificationRepository.save(entity);
            PushNotificationMessage responseToBeSent = prepareMessage(response, userEntity.getFirstName(), userEntity.getLanguage());
            return ResponseEntity.ok(responseToBeSent);

        }else{
            return ResponseEntity.notFound().build();
        }

    }

    private PushNotificationMessage prepareMessage(PushNotificationMessage response, String username, String language) {
        StringBuilder location = new StringBuilder();
        PushNotificationMessage pushNotificationMessage = PushNotificationMessage.builder().build();
        location.append(response.getCity()).append(", ").append(response.getCountry());
        try {
            Date date =new SimpleDateFormat(dateFormat).parse(response.getTime());
            String strTimeDatePart  = date.getDate() + " " + Util.getMonth(date.getMonth()) + " " + (date.getYear()+1900);
            String h = date.getHours() == 0 ? " hour " : " hours ";
            String m = date.getMinutes() == 0 ? " minutes " : " minutes ";
            String s = date.getSeconds() == 0 ? " seconds" : " seconds";
            String timePart = (date.getHours()+1) + h + + (date.getMinutes()+1) + m + (date.getSeconds()+1) + s;
            String formattedMessage = String.format(message, username, response.getAmount(), response.getReceiverName(),
                    location, strTimeDatePart, timePart);
            pushNotificationMessage.setId(response.getId());
            pushNotificationMessage.setMessage(formattedMessage);
            pushNotificationMessage.setLanguage(language);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return pushNotificationMessage;
    }

    public PushNotificationEntity savePushNotification(PushNotificationEntity pushNotificationEntity){
        return pushNotificationRepository.save(pushNotificationEntity);
    }

    public ResponseEntity<String> authorisePushNotification(PushNotificationRequest pushNotificationRequest) {
        //get push notification
        Optional<PushNotificationEntity> entityOptional = pushNotificationRepository.findById(Long.parseLong(pushNotificationRequest.getPushNotificationId()));
        if(entityOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        PushNotificationEntity entity = entityOptional.get();
        entity.setStatus(pushNotificationRequest.getStatus());

        //update Status
        pushNotificationRepository.save(entity);

        //Generate OTP
        otpService.createOTP(entity.getUsername());

        return ResponseEntity.ok("SUCCESS");
    }
}
