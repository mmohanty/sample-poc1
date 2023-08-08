package com.ezbank.service;

import com.ezbank.entity.PushNotificationEntity;
import com.ezbank.entity.TransactionEntity;
import com.ezbank.entity.UserEntity;
import com.ezbank.external.client.IpLocationServiceClient;
import com.ezbank.model.request.TransactionAuthenticationRequest;
import com.ezbank.model.request.TransactionRequest;
import com.ezbank.model.response.*;
import com.ezbank.model.response.ip.IPLocation;
import com.ezbank.repository.PushNotificationRepository;
import com.ezbank.repository.TransactionRepository;
import com.ezbank.repository.UserRepository;
import com.ezbank.utils.Util;
import com.google.gson.Gson;
import com.maxmind.geoip2.DatabaseReader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPService otpService;

    private Map<String, Map<String, TransactionRequest>> map = new ConcurrentHashMap<>();

    @Autowired
    private PushNotificationRepository pushNotificationRepository;

    @Autowired
    private IpLocationServiceClient ipLocationServiceClient;

    @Value("${apiip.key}")
    private String apiipKey;

    @Value("${ip.location.service}")
    private String ipLocationCall;

    @Value("${date.format}")
    private String dateFormat;

    @Autowired
    private DatabaseReader reader;

    private Map<String, Integer> transactionFailuerCountMap = new ConcurrentHashMap<>();

    public List<TransactionHistoryResponse> getAllTransactions(String username) {

        List<TransactionEntity> userEntity = transactionRepository.findByUsername(username);
        List<TransactionHistoryResponse> responses = new ArrayList<>();
        if(userEntity != null){
            userEntity.forEach(item -> {
                TransactionHistoryResponse transactionResponse = TransactionHistoryResponse.builder()
                        .id(item.getId().toString()).
                         status(Status.valueOf(item.getStatus()))
                        .amount(item.getAmount()).time(item.getTime())
                        .location(item.getLocation()).time(item.getTime())
                        .receiverName(item.getReceiverName()).build();
                responses.add(transactionResponse);
            });
        }
        return responses;
    }

    public ResponseEntity<TransactionResponse> authorize(TransactionAuthenticationRequest request) {

        //fetch transaction from transaction cache
        UserEntity entity = userRepository.findByCardNumber(request.getCardNumber());
        TransactionRequest orgRequest = map.get(entity.getUsername()).get(request.getTransactionId());
        if(orgRequest == null){
            System.out.println("No Original transaction found for user " + entity.getUsername() + " transaction Id :" + request.getTransactionId());
            return ResponseEntity.badRequest().build();
        }
        String otpMessage = otpService.getOtp(entity.getUsername());
        if(otpMessage == null || !transactionFailuerCountMap.containsKey(request.getTransactionId())){
            System.out.println("No transaction found for user " + entity.getUsername() + " transaction Id :" + request.getTransactionId());
            return ResponseEntity.badRequest().body(TransactionResponse.builder().status("INVALID_TRANSACTION").build());
        }

        if(!StringUtils.equalsIgnoreCase(request.getOtp(), otpMessage)){
            System.out.println("No OTP found for user " + entity.getUsername() + " transaction Id :" + request.getTransactionId());
            int remainingAttempt = transactionFailuerCountMap.get(request.getTransactionId());
            if(remainingAttempt == 1){
                transactionFailuerCountMap.remove(request.getTransactionId());
            }
            return ResponseEntity.badRequest().body(TransactionResponse.builder().status("INVALID_OTP")
                    .remainingAttempt(remainingAttempt).build());
        }

        PushNotificationEntity pushNotificationEntity = pushNotificationRepository.findByUsernameAndTransactionId(entity.getUsername(), request.getTransactionId());
        //approve transaction if PN has been  approved
        if("APPROVED".equalsIgnoreCase(pushNotificationEntity.getStatus())){
            //Store transaction in DB and debit balance
            String body = pushNotificationEntity.getMessage();
            Gson gson = new Gson();
            PushNotificationMessage pnm = gson.fromJson(body, PushNotificationMessage.class);
            Map<String, String> map = new HashMap<>();
            map.put("city", pnm.getCity());
            map.put("country", pnm.getCountry());
            map.put("region", pnm.getRegionName());
            TransactionEntity transactionEntity = TransactionEntity.builder()
                    .id(request.getTransactionId())
                    .amount(orgRequest.getAmount())
                    .receiverName(orgRequest.getReceiverName())
                    .username(entity.getUsername())
                    .message(pushNotificationEntity.getMessage())
                    .time(orgRequest.getTime())
                    .status("APPROVED")
                    .location(gson.toJson(map)).build();
            transactionRepository.save(transactionEntity);
            TransactionResponse response = TransactionResponse.builder().id(request.getTransactionId()).status("SUCCESS").build();
            return ResponseEntity.ok(response);
        }else{
            System.out.println("PN is not approved for " + entity.getUsername() + " transaction Id :" + request.getTransactionId());
            return ResponseEntity.badRequest().build();
        }

    }


    public ResponseEntity<TransactionResponse> initiate(TransactionRequest request) {

        //fetch username
        String expiryDate = request.getExpiryMonth() + "-" + request.getExpiryYear();
        UserEntity entity = userRepository.findByCardNumberAndCvvAndExpiryDate(request.getCardNumber(), request.getCvv(), expiryDate);
        if(entity == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        //find location using ip
        /*IPLocation ipLocation = null;
        if(StringUtils.equalsIgnoreCase("stubbed", ipLocationCall)){
            try {
                String str = IOUtils.toString(this.getClass().getResourceAsStream("/sample/iplocation-sample.json"),
                        "UTF-8");
                Gson gson = new Gson();
                ipLocation = gson.fromJson(str, IPLocation.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            ipLocation = ipLocationServiceClient.getLocation(request.getIp(), apiipKey);
        }*/
        Location location = null;
        if(StringUtils.equalsIgnoreCase("stubbed", ipLocationCall)){
                location = Location.builder()
                        .city("Hyderabad")
                        .country("India").build();
        }else {
            location = Util.getLocation(reader, request.getIp());
        }
        String date = new SimpleDateFormat(dateFormat).format(new Date());
        PushNotificationMessage pushNotificationMessage = PushNotificationMessage.builder()
                .regionName(location.getCity())
                .receiverName(request.getReceiverName())
                .time(date)
                .amount(request.getAmount().toString())
                .country(location.getCountry())
                .transactionType("DEBIT")
                .city(location.getCity()).build();

        //Store Transaction in cache
        String transactionId = "TEZ-" + RandomStringUtils.randomAlphanumeric(10).toUpperCase();;
        request.setTime(date);
        if(map.containsKey(entity.getUsername())){
            map.get(entity.getUsername()).put(transactionId, request);
        }else {
            Map<String, TransactionRequest> req = new HashMap<>();
            req.put(transactionId, request);
            map.put(entity.getUsername(), req);
        }

        //generates push notification
        Gson gson = new Gson();
        String message = gson.toJson(pushNotificationMessage);
        PushNotificationEntity pushNotificationEntity = PushNotificationEntity.builder()
                .message(message).username(entity.getUsername()).status("UNREAD")
                .transactionId(transactionId).build();
        pushNotificationRepository.save(pushNotificationEntity);

        transactionFailuerCountMap.put(transactionId, 3);
        return ResponseEntity.ok(TransactionResponse.builder().id(transactionId).status("INITIATED").build());

    }

}
