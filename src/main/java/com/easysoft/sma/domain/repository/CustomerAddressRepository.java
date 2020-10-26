package com.easysoft.sma.domain.repository;

import java.util.List;

import com.easysoft.sma.domain.entity.CustomerAddress;

import org.springframework.data.repository.CrudRepository;

public interface CustomerAddressRepository extends CrudRepository<CustomerAddress, String> {
    
    public List<CustomerAddress> findByCustomerIdAndCategory(String customerId, String category);

    public void deleteByCustomerId(String customerId);
}
