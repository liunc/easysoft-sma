package com.easysoft.sma.domain.dto;

public class CustomerCategoryPageRequest {

	private String name;

	private String status;

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

	public CustomerCategoryPageRequest() {

	}

	public CustomerCategoryPageRequest(String name, String status) {
		this.name = name;
		this.status = status;
	}

}
