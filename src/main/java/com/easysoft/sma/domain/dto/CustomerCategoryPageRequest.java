package com.easysoft.sma.domain.dto;

import com.easysoft.lib.jdb.domain.dto.PageRequest;

public class CustomerCategoryPageRequest extends PageRequest {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
