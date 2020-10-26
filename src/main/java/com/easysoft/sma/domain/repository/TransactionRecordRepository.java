package com.easysoft.sma.domain.repository;

import java.util.List;

import com.easysoft.sma.domain.entity.TransactionRecord;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRecordRepository extends CrudRepository<TransactionRecord, String> {
    
    public List<TransactionRecord> findByCustomerId(String customerId);

    public void deleteByCustomerId(String customerId);
}
