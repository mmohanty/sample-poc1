package com.ezbank.repository;

import com.ezbank.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

    public List<TransactionEntity> findByUsername(String username);
}
