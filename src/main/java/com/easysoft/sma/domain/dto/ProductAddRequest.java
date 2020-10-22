package com.easysoft.sma.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductAddRequest {
    
    @NotBlank(message = "{product_category}{validator_required}")
	private String categoryId;

	private int salesYear;

	@NotBlank(message = "{pack_unit}{validator_required}")
	@Size(max = 10, message = "{pack_unit}{validator_max_length}")
	private String packUnit;

	@Min(value = 0, message = "{price}{validator_min}")
	private BigDecimal price;

	@Min(value = 0, message = "{spec}{validator_min}")
	private BigDecimal spec;

	@NotBlank(message = "{spec_unit}{validator_required}")
	@Size(max = 10, message = "{spec_unit}{validator_max_length}")
	private String specUnit;

	@NotBlank(message = "{support_delivery_mode}{validator_required}")
	@Size(max = 20, message = "{support_delivery_mode}{validator_max_length}")
	private String supportDeliveryMode;

	@Size(max = 512, message = "{remark}{validator_max_length}")
	private String remark;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getSalesYear() {
		return salesYear;
	}

	public void setSalesYear(int salesYear) {
		this.salesYear = salesYear;
	}

	public String getPackUnit() {
		return packUnit;
	}

	public void setPackUnit(String packUnit) {
		this.packUnit = packUnit;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSpec() {
		return spec;
	}

	public void setSpec(BigDecimal spec) {
		this.spec = spec;
	}

	public String getSpecUnit() {
		return specUnit;
	}

	public void setSpecUnit(String specUnit) {
		this.specUnit = specUnit;
	}

	public String getSupportDeliveryMode() {
		return supportDeliveryMode;
	}

	public void setSupportDeliveryMode(String supportDeliveryMode) {
		this.supportDeliveryMode = supportDeliveryMode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}