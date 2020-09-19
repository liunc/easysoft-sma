package com.easysoft.sma.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerCategoryAddRequest {

	@NotBlank(message = "{name}{validator_required}")
	@Size(max = 40, message = "{name}{validator_max_length}")
	private String name;
	
	@Size(max = 512, message = "{remark}{validator_max_length}")
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

}
