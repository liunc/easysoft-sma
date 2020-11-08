package com.easysoft.sma.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.easysoft.sma.domain.entity.QCustomer;
import com.easysoft.sma.domain.entity.QCustomerCategory;
import com.easysoft.sma.domain.entity.QTransactionRecord;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.data.domain.Sort;

public class TransactionPageRow {

    private String customerCategoryName;

    private String customerWechatName;

    private String customerName;

    private String category;

    private String source;

    private BigDecimal amount;

    private Date recordTime;

    private String remark;

    public String getCustomerCategoryName() {
        return customerCategoryName;
    }

    public void setCustomerCategoryName(String customerCategoryName) {
        this.customerCategoryName = customerCategoryName;
    }

    public String getCustomerWechatName() {
        return customerWechatName;
    }

    public void setCustomerWechatName(String customerWechatName) {
        this.customerWechatName = customerWechatName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static void setOrder(JPAQuery<TransactionPageRow> query, QTransactionRecord qtr, QCustomer qc,
            QCustomerCategory qcc, Sort sort) {

        if (!sort.iterator().hasNext()) {
            query.orderBy(orderByDesc(qtr, qc, qcc, ""));
            return;
        }
        for (Sort.Order o : sort) {

            String property = o.getProperty();
            OrderSpecifier<?> spec = o.isAscending() ? orderByAsc(qtr, qc, qcc, property)
                    : orderByDesc(qtr, qc, qcc, property);

            query.orderBy(spec);
        }
    }

    private static OrderSpecifier<?> orderByAsc(QTransactionRecord qtr, QCustomer qc, QCustomerCategory qcc,
            String property) {
        OrderSpecifier<?> spec = null;
        switch (property) {

            case "customerCategoryName":
                spec = qcc.name.asc();
                break;

            case "customerWechatName":
                spec = qc.wechatName.asc();
                break;

            case "customerName":
                spec = qc.name.asc();
                break;

            case "category":
                spec = qtr.category.asc();
                break;

            case "source":
                spec = qtr.source.asc();
                break;

            case "amount":
                spec = qtr.amount.asc();
                break;

            case "recordTime":
                spec = qtr.recordTime.asc();
                break;

            case "remark":
                spec = qtr.remark.asc();
                break;

            default:
                spec = qtr.recordTime.asc();
                break;
        }
        return spec;
    }

    private static OrderSpecifier<?> orderByDesc(QTransactionRecord qtr, QCustomer qc, QCustomerCategory qcc,
            String property) {
        OrderSpecifier<?> spec = null;
        switch (property) {
            case "customerCategoryName":
                spec = qcc.name.desc();
                break;

            case "customerWechatName":
                spec = qc.wechatName.desc();
                break;

            case "customerName":
                spec = qc.name.desc();
                break;

            case "category":
                spec = qtr.category.desc();
                break;

            case "source":
                spec = qtr.source.desc();
                break;

            case "amount":
                spec = qtr.amount.desc();
                break;

            case "recordTime":
                spec = qtr.recordTime.desc();
                break;

            case "remark":
                spec = qtr.remark.desc();
                break;

            default:
                spec = qtr.recordTime.desc();
                break;
        }
        return spec;
    }

}