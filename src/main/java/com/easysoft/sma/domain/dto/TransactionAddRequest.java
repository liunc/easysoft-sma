package com.easysoft.sma.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TransactionAddRequest {

	@NotBlank(message = "{customer_id}{validator_required}")
	private String customerId;
	
	@NotBlank(message = "{transaction_category}{validator_required}")
	private String category;

	@NotBlank(message = "{transaction_source}{validator_required}")
	@Size(max = 40, message = "{transaction_source}{validator_max_length}")
	private String source;

	@Min(value = 0, message = "{amount}{validator_min}")
	private BigDecimal amount;

	@Size(max = 512, message = "{remark}{validator_max_length}")
	private String remark;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
