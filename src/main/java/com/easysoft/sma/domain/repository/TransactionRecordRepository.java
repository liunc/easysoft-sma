package com.easysoft.sma.domain.repository;

import java.util.List;

import com.easysoft.lib.jdb.domain.repository.BaseRepository;
import com.easysoft.sma.domain.entity.TransactionRecord;

public interface TransactionRecordRepository extends BaseRepository<TransactionRecord, String> {
    
    public List<TransactionRecord> findByCustomerId(String customerId);

    public void deleteByCustomerId(String customerId);
}
