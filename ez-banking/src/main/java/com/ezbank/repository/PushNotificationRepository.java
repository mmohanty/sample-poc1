package com.ezbank.repository;

import com.ezbank.entity.PushNotificationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushNotificationRepository extends CrudRepository<PushNotificationEntity, Long> {

    public List<PushNotificationEntity> findByUsernameAndStatusOrderById(String username, String status);

    PushNotificationEntity findByUsernameAndTransactionId(String username, String transactionId);
}
