package com.easysoft.sma.domain.repository;

import java.util.List;

import com.easysoft.lib.jdb.domain.repository.BaseRepository;
import com.easysoft.sma.domain.entity.SalesOrder;

public interface SalesOrderRepository extends BaseRepository<SalesOrder, String> {

	public boolean existsByCustomerId(String customerId);

    public List<SalesOrder> findByCustomerIdAndStatus(String customerId, String status);
}
