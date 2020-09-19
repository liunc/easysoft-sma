package com.easysoft.sma.domain.dto;

import com.easysoft.lib.jdb.domain.dto.DetailResponse;
import com.easysoft.sma.domain.entity.CustomerCategory;

public class CustomerCategoryDetailResponse extends DetailResponse {

	private String name;

	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public CustomerCategoryDetailResponse() {
	}

	public CustomerCategoryDetailResponse(CustomerCategory entity) {
		super(entity.getId(), entity.getCreater(), entity.getCreateTime(), entity.getUpdater(), entity.getUpdateTime());
		this.name = entity.getName();
		this.remark = entity.getRemark();
	}
}
