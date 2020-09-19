package com.easysoft.sma.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easysoft.sma.domain.entity.SalesOrder;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, String> {

	public boolean existsByCustomerId(String customerId);

    public List<SalesOrder> findByCustomerIdAndStatus(String customerId, String status);
}
