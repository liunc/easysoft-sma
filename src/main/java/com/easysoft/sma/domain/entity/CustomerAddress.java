package com.easysoft.sma.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.easysoft.sma.domain.valueobject.AddressCategory;

@Entity
@Table(name = "customer_address")
public class CustomerAddress extends BaseEntity {

	private static final long serialVersionUID = -8116240182698898840L;

	@Column(name = "customer_id", columnDefinition = "CHAR(32) COMMENT '客户ID'")
	private String customerId;

	@Column(columnDefinition = "CHAR(1) COMMENT '地址类型 1寄件地址 2收件地址'", nullable = false)
	private String category;

	@Column(columnDefinition = "VARCHAR(40) COMMENT '联系人'", nullable = false)
	private String linkman;

	@Column(columnDefinition = "VARCHAR(20) COMMENT '电话'", nullable = false)
	private String telephone;

	@Column(columnDefinition = "VARCHAR(120) COMMENT '地址'", nullable = false)
	private String address;

	public String getCustomerId() {
		return customerId;
	}

	public String getCategory() {
		return category;
	}

	public String getLinkman() {
		return linkman;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getAddress() {
		return address;
	}

	public void create(String customerId, String category, String linkman, String telephone, String address) {

		super.create();
		this.customerId = customerId;
		this.category = AddressCategory.get(category);
		this.linkman = linkman;
		this.telephone = telephone;
		this.address = address;
	}

	public void update(String linkman, String telephone, String address) {

		this.linkman = linkman;
		this.telephone = telephone;
		this.address = address;
	}
}
