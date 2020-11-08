package com.easysoft.sma.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.easysoft.sma.domain.entity.QCustomer;
import com.easysoft.sma.domain.entity.QCustomerCategory;
import com.easysoft.sma.domain.entity.QSalesOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.data.domain.Sort;

public class SalesOrderPageRow {

	private String id;

	private String customerId;

	private String customerCategoryName;

	private String customerWechatName;

	private String customerName;

	private String sender;

	private String senderTelephone;

	private String senderAddress;

	private String consignee;

	private String consigneeTelephone;

	private String consigneeAddress;

	private int sequenceNumber;

	private BigDecimal amountCalculated;

	private BigDecimal amountReceived;

	private BigDecimal discountedAmount;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date scheduleDate;

	private Date orderTime;

	private String deliveryMode;

	private String status;

	private String snapshot;

	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

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

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderTelephone() {
		return senderTelephone;
	}

	public void setSenderTelephone(String senderTelephone) {
		this.senderTelephone = senderTelephone;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}

	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public BigDecimal getAmountCalculated() {
		return amountCalculated;
	}

	public void setAmountCalculated(BigDecimal amountCalculated) {
		this.amountCalculated = amountCalculated;
	}

	public BigDecimal getAmountReceived() {
		return amountReceived;
	}

	public void setAmountReceived(BigDecimal amountReceived) {
		this.amountReceived = amountReceived;
	}

	public BigDecimal getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(BigDecimal discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static void setOrder(JPAQuery<SalesOrderPageRow> query, QSalesOrder so, QCustomer c, QCustomerCategory cc,
			Sort sort) {

		if (!sort.iterator().hasNext()) {
			query.orderBy(orderByAsc(so, c, cc, ""));
			return;
		}

		for (Sort.Order o : sort) {

			String property = o.getProperty();
			OrderSpecifier<?> spec = o.isAscending() ? orderByAsc(so, c, cc, property)
					: orderByDesc(so, c, cc, property);

			query.orderBy(spec);
		}
	}

	private static OrderSpecifier<?> orderByAsc(QSalesOrder so, QCustomer c, QCustomerCategory cc, String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {
			case "id":
				spec = so.id.asc();
				break;

			case "customerId":
				spec = c.id.asc();
				break;

			case "customerCategoryName":
				spec = cc.name.asc();
				break;

			case "customerWechatName":
				spec = c.wechatName.asc();
				break;

			case "customerName":
				spec = c.name.asc();
				break;

			case "sender":
				spec = so.sender.asc();
				break;

			case "senderTelephone":
				spec = so.senderTelephone.asc();
				break;

			case "senderAddress":
				spec = so.senderAddress.asc();
				break;

			case "consignee":
				spec = so.consignee.asc();
				break;

			case "consigneeTelephone":
				spec = so.consigneeTelephone.asc();
				break;

			case "consigneeAddress":
				spec = so.consigneeAddress.asc();
				break;

			case "sequenceNumber":
				spec = so.sequenceNumber.asc();
				break;

			case "amountCalculated":
				spec = so.amountCalculated.asc();
				break;

			case "amountReceived":
				spec = so.amountReceived.asc();
				break;

			case "discountedAmount":
				spec = so.discountedAmount.asc();
				break;

			case "scheduleDate":
				spec = so.scheduleDate.asc();
				break;

			case "deliveryMode":
				spec = so.deliveryMode.asc();
				break;

			case "status":
				spec = so.status.asc();
				break;

			case "snapshot":
				spec = so.snapshot.asc();
				break;

			case "remark":
				spec = so.remark.asc();
				break;

			case "orderTime":
				spec = so.createTime.asc();
				break;

			default:
				spec = so.createTime.asc();
				break;
		}
		return spec;
	}

	private static OrderSpecifier<?> orderByDesc(QSalesOrder so, QCustomer c, QCustomerCategory cc, String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {
			case "id":
				spec = so.id.desc();
				break;

			case "customerId":
				spec = c.id.desc();
				break;

			case "customerCategoryName":
				spec = cc.name.desc();
				break;

			case "customerWechatName":
				spec = c.wechatName.desc();
				break;

			case "customerName":
				spec = c.name.desc();
				break;

			case "sender":
				spec = so.sender.desc();
				break;

			case "senderTelephone":
				spec = so.senderTelephone.desc();
				break;

			case "senderAddress":
				spec = so.senderAddress.desc();
				break;

			case "consignee":
				spec = so.consignee.desc();
				break;

			case "consigneeTelephone":
				spec = so.consigneeTelephone.desc();
				break;

			case "consigneeAddress":
				spec = so.consigneeAddress.desc();
				break;

			case "sequenceNumber":
				spec = so.sequenceNumber.desc();
				break;

			case "amountCalculated":
				spec = so.amountCalculated.desc();
				break;

			case "amountReceived":
				spec = so.amountReceived.desc();
				break;

			case "discountedAmount":
				spec = so.discountedAmount.desc();
				break;

			case "scheduleDate":
				spec = so.scheduleDate.desc();
				break;

			case "deliveryMode":
				spec = so.deliveryMode.desc();
				break;

			case "status":
				spec = so.status.desc();
				break;

			case "snapshot":
				spec = so.snapshot.desc();
				break;

			case "remark":
				spec = so.remark.desc();
				break;

			case "orderTime":
				spec = so.createTime.desc();
				break;

			default:
				spec = so.createTime.desc();
				break;
		}
		return spec;
	}

}