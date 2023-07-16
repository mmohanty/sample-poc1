package com.ezbank.repository;

import com.ezbank.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    public UserEntity findByUsernameAndPassword(String username, String password);
    public UserEntity findByUsername(String username);

    UserEntity findByCardNumberAndCvvAndExpiryDate(String cardNumber, String cvv, String expiryDate);

    UserEntity findByCardNumber(String cardNumber);
}
