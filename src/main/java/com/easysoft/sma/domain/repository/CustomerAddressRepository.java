package com.easysoft.sma.domain.repository;

import java.util.List;

import com.easysoft.lib.jdb.domain.repository.BaseRepository;
import com.easysoft.sma.domain.entity.CustomerAddress;

public interface CustomerAddressRepository extends BaseRepository<CustomerAddress, String> {
    
    public List<CustomerAddress> findByCustomerIdAndCategory(String customerId, String category);

    public void deleteByCustomerId(String customerId);
}
