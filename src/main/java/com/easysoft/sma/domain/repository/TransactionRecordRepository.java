package com.easysoft.sma.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easysoft.sma.domain.entity.TransactionRecord;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, String> {
    
    public List<TransactionRecord> findByCustomerId(String customerId);

    public void deleteByCustomerId(String customerId);
}
