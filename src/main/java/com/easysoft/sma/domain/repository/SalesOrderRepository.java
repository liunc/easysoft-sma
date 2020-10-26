package com.easysoft.sma.domain.repository;

import java.util.List;

import com.easysoft.sma.domain.entity.SalesOrder;

import org.springframework.data.repository.CrudRepository;

public interface SalesOrderRepository extends CrudRepository<SalesOrder, String> {

	public boolean existsByCustomerId(String customerId);

    public List<SalesOrder> findByCustomerIdAndStatus(String customerId, String status);
}
