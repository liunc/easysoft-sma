package com.easysoft.sma.domain.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.sma.domain.dto.SalesOrderPageRow;
import com.easysoft.sma.domain.dto.SalesOrderProductAddRequest;
import com.easysoft.sma.domain.dto.SalesOrderProductItem;
import com.easysoft.sma.domain.entity.SalesOrder;
import org.springframework.data.domain.Pageable;

public interface OrderService {

        public PageResponse<SalesOrderPageRow> findSalesOrder(String wechatName, String name, String consignee,
                        String consigneeTelephone, String deliveryMode, String delay, String status, Pageable pageable);

        public void create(String customerId, String sender, String senderTelephone, String senderAddress,
                        String consignee, String consigneeTelephone, String consigneeAddress, int sequenceNumber,
                        BigDecimal amountCalculated, BigDecimal discountedAmount, String deliveryMode, String delay, String remark,
                        List<SalesOrderProductAddRequest> products) throws BusinessException;

        public SalesOrder findOrder(String id) throws BusinessException;

        public void update(String id, String sender, String senderTelephone, String senderAddress, String consignee,
                        String consigneeTelephone, String consigneeAddress, int sequenceNumber,
                        BigDecimal amountCalculated, BigDecimal discountedAmount, String deliveryMode, String delay, String remark,
                        List<SalesOrderProductAddRequest> products) throws BusinessException;

        public List<SalesOrderProductItem> findSalesOrderProduct(String orderId);

        public void delete(String id) throws BusinessException;

        public void cancel(String id) throws BusinessException;

        public void schedule(Date scheduleDate, List<String> orderIds) throws BusinessException;

        public void undoSchedule(String id) throws BusinessException;

        public String exportScheduled(String wechatName, String consigneeTelephone, String deliveryMode);

        public void placeOrder(List<String> orderIds) throws BusinessException;

        public void receive(List<String> orderIds) throws BusinessException;
}