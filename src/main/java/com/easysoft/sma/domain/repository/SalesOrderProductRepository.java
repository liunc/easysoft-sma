package com.easysoft.sma.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easysoft.sma.domain.entity.SalesOrderProduct;

public interface SalesOrderProductRepository extends JpaRepository<SalesOrderProduct, String> {

    public void deleteByOrderId(String orderId);

}
