package com.easysoft.sma.domain.dto;

public class ProductPageRequest {
	private String categoryId;

	private int salesYear;

	private String name;

	private String status;

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the salesYear
	 */
	public int getSalesYear() {
		return salesYear;
	}

	/**
	 * @param salesYear the wechatName to set
	 */
	public void setSalesYear(int salesYear) {
		this.salesYear = salesYear;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public ProductPageRequest(String categoryId, int salesYear, String name, String status) {
		this.categoryId = categoryId;
		this.salesYear = salesYear;
		this.name = name;
		this.status = status;
	}

	public ProductPageRequest() {
	}

}