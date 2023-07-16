package com.ezbank.service;

import com.ezbank.entity.UserEntity;
import com.ezbank.model.request.LoginRequest;
import com.ezbank.model.response.User;
import com.ezbank.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<User> authenticateUser(LoginRequest request){
        if(StringUtils.isEmpty(request.getUsername())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserEntity entity = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if(entity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = User.builder().id(entity.getId()).firstName(entity.getFirstName())
                .lastName(entity.getLastName()).username(entity.getUsername()).language(entity.getLanguage())
                .accountNumber(entity.getAccountNumber())
                .cardNumber(entity.getCardNumber())
                .balance(entity.getBalance())
                .expiryDate(entity.getExpiryDate())
                .cardType(entity.getCardType())
                .cardCategory(entity.getCardCategory())
                .cvv("xxx").build();

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    public ResponseEntity<UserEntity> getUserByName(String username) {
        UserEntity entity = userRepository.findByUsername(username);

        if(entity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    public ResponseEntity<User> getUser(String id) {
        Optional<UserEntity> entityOptional = userRepository.findById(Long.parseLong(id));

        if(entityOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserEntity entity = entityOptional.get();

        User user = User.builder().id(entity.getId()).firstName(entity.getFirstName())
                .lastName(entity.getLastName()).username(entity.getUsername()).language(entity.getLanguage()).build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<User> createUser(User request) {
        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .balance(request.getBalance())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .accountNumber(request.getAccountNumber())
                .cardNumber(request.getCardNumber())
                .cardCategory(request.getCardCategory())
                .cardType(request.getCardType())
                .cvv(request.getCvv())
                .expiryDate(request.getExpiryDate())
                .language(request.getLanguage())
                .build();
        userRepository.save(userEntity);
        request.setId(userEntity.getId());
        return ResponseEntity.ok(request);
    }

    public ResponseEntity<User> updateUser(User request) {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername());
        if(userEntity == null){
            return ResponseEntity.badRequest().build();
        }

        if(StringUtils.isNoneBlank(request.getFirstName())) {
            userEntity.setFirstName(request.getFirstName());
        }
        if(StringUtils.isNoneBlank(request.getLastName())) {
            userEntity.setLastName(request.getLastName());
        }
        if(StringUtils.isNoneBlank(request.getLanguage())) {
            userEntity.setLanguage(request.getLanguage());
        }
        if(StringUtils.isNoneBlank(request.getCvv())) {
            userEntity.setCvv(request.getCvv());
        }
        if(StringUtils.isNoneBlank(request.getExpiryDate())) {
            userEntity.setExpiryDate(request.getExpiryDate());
        }
        userRepository.save(userEntity);
        return ResponseEntity.ok(request);
    }
}
