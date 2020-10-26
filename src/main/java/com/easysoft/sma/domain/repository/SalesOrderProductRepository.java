package com.easysoft.sma.domain.repository;

import com.easysoft.sma.domain.entity.SalesOrderProduct;

import org.springframework.data.repository.CrudRepository;

public interface SalesOrderProductRepository extends CrudRepository<SalesOrderProduct, String> {

    public void deleteByOrderId(String orderId);

}
