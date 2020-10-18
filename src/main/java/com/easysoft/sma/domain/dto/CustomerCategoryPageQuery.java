package com.easysoft.sma.domain.dto;

public class CustomerCategoryPageQuery {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustomerCategoryPageQuery(){

	}

	public CustomerCategoryPageQuery(String name){
		this.name = name;
	}
}
