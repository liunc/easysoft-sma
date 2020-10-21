package com.easysoft.sma.domain.dto;

import com.easysoft.lib.jdb.domain.dto.DetailResponse;
import com.easysoft.sma.domain.entity.ProductCategory;

public class ProductCategoryDetailResponse extends DetailResponse {

	private String name;

	private String status;

	private String remark;

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

	public ProductCategoryDetailResponse() {
	}

	public ProductCategoryDetailResponse(ProductCategory entity) {
		super(entity.getId(), entity.getCreater(), entity.getCreateTime(), entity.getUpdater(), entity.getUpdateTime());
		this.name = entity.getName();
		this.status = entity.getStatus();
		this.remark = entity.getRemark();
	}

}
