package com.easysoft.sma.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerAddressUpdateRequest {

	@NotBlank(message = "{id}{validator_required}")
	private String id;

	@NotBlank(message = "{linkman}{validator_required}")
	@Size(max = 40, message = "{linkman}{validator_max_length}")
	private String linkman;

	@NotBlank(message = "{telephone}{validator_required}")
	@Size(max = 20, message = "{telephone}{validator_max_length}")
	private String telephone;

	@NotBlank(message = "{address}{validator_required}")
	@Size(max = 120, message = "{address}{validator_max_length}")
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
