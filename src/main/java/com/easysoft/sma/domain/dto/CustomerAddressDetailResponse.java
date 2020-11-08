package com.easysoft.sma.domain.dto;

import com.easysoft.lib.jdb.domain.dto.DetailResponse;
import com.easysoft.sma.domain.entity.CustomerAddress;

public class CustomerAddressDetailResponse extends DetailResponse {

	private String customerId;

	private String category;

	private String linkman;

	private String telephone;

	private String address;

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

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CustomerAddressDetailResponse() {
	}

	public CustomerAddressDetailResponse(CustomerAddress entity) {
		super(entity.getId(), entity.getCreater(), entity.getCreateTime(), entity.getUpdater(), entity.getUpdateTime());

		this.customerId = entity.getCustomerId();
		this.category = entity.getCategory();
		this.linkman = entity.getLinkman();
		this.telephone = entity.getTelephone();
		this.address = entity.getAddress();
	}
}
