package com.easysoft.sma.domain.dto;

import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

import com.easysoft.sma.domain.entity.QCustomer;
import com.easysoft.sma.domain.entity.QCustomerCategory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

public class CustomerPageRow {

	private String id;

	private String categoryName;

	private String wechatName;

	private String name;

	private BigDecimal balance;

	private String status;

	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static void setOrder(JPAQuery<CustomerPageRow> query, QCustomerCategory qcc, QCustomer qc, Sort sort) {

		for (Sort.Order o : sort) {

			String property = o.getProperty();
			OrderSpecifier<?> spec = o.isAscending() ? orderByAsc(qcc, qc, property) : orderByDesc(qcc, qc, property);

			if (spec != null) {
				query.orderBy(spec);
			}
		}

	}

	private static OrderSpecifier<?> orderByAsc(QCustomerCategory qcc, QCustomer qc, String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {

			case "categoryName":
				spec = qcc.name.asc();
				break;
				
			case "wechatName":
				spec = qc.wechatName.asc();
				break;

			case "name":
				spec = qc.name.asc();
				break;

			case "status":
				spec = qc.status.asc();
				break;
		}
		return spec;
	}

	private static OrderSpecifier<?> orderByDesc(QCustomerCategory qcc, QCustomer qc, String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {

			case "categoryName":
				spec = qcc.name.desc();
				break;

			case "wechatName":
				spec = qc.wechatName.desc();
				break;

			case "name":
				spec = qc.name.desc();
				break;

			case "status":
				spec = qc.status.desc();
				break;
		}
		return spec;
	}

	

}
