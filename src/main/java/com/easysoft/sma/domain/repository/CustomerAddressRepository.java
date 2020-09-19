package com.easysoft.sma.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easysoft.sma.domain.entity.CustomerAddress;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, String> {
    
    public List<CustomerAddress> findByCustomerIdAndCategory(String customerId, String category);

    public void deleteByCustomerId(String customerId);
}
