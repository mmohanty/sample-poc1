package com.ezbank.controller;

import com.ezbank.entity.UserEntity;
import com.ezbank.model.request.LoginRequest;
import com.ezbank.model.response.User;
import com.ezbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins= {"http://localhost:4200", "http://localhost:4100"})
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<User> authenticate(@RequestBody LoginRequest request){
        return userService.authenticateUser(request);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<User> createUser(@RequestBody User request){
        return userService.createUser(request);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<User> updateUser(@RequestBody User request){
        return userService.updateUser(request);
    }

    @GetMapping(path = "/name/{username}")
    public ResponseEntity<UserEntity> getUserByName(@PathVariable String username){
        return userService.getUserByName(username);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        return userService.getUser(id);
    }
}
