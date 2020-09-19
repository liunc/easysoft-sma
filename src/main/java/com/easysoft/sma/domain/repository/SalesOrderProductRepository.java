package com.easysoft.sma.domain.repository;

import com.easysoft.lib.jdb.domain.repository.BaseRepository;
import com.easysoft.sma.domain.entity.SalesOrderProduct;

public interface SalesOrderProductRepository extends BaseRepository<SalesOrderProduct, String> {

    public void deleteByOrderId(String orderId);

}
