package com.easysoft.sma.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductCategoryUpdateRequest {

	@NotBlank(message = "{id}{validator_required}")
	private String id;

	@NotBlank(message = "{name}{validator_required}")
	@Size(max = 40, message = "{name}{validator_max_length}")
	private String name;

	@Size(max = 512, message = "{remark}{validator_max_length}")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

}
