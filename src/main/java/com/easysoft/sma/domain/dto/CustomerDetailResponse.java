package com.easysoft.sma.domain.dto;

import java.math.BigDecimal;

import com.easysoft.lib.jdb.domain.dto.DetailResponse;

public class CustomerDetailResponse extends DetailResponse {
 
	private String categoryName;

	private String wechatName;

	private String name;

	private String status;

	private String remark;

	private BigDecimal balance;

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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
