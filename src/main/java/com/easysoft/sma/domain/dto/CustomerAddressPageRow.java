package com.easysoft.sma.domain.dto;

import com.easysoft.sma.domain.entity.QCustomer;
import com.easysoft.sma.domain.entity.QCustomerAddress;
import com.easysoft.sma.domain.entity.QCustomerCategory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.data.domain.Sort;

public class CustomerAddressPageRow {

	private String id;

	private String customerId;

	private String customerCategoryName;

	private String customerWechatName;

	private String customerName;

	private String category;

	private String linkman;

	private String telephone;

	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCategoryName() {
		return customerCategoryName;
	}

	public void setCustomerCategoryName(String customerCategoryName) {
		this.customerCategoryName = customerCategoryName;
	}

	public String getCustomerWechatName() {
		return customerWechatName;
	}

	public void setCustomerWechatName(String customerWechatName) {
		this.customerWechatName = customerWechatName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public static void setOrder(JPAQuery<CustomerAddressPageRow> query, QCustomerAddress ca, QCustomer c,
			QCustomerCategory cc, Sort sort) {

		for (Sort.Order o : sort) {

			String property = o.getProperty();
			OrderSpecifier<?> spec = o.isAscending() ? orderByAsc(ca, c, cc, property)
					: orderByDesc(ca, c, cc, property);

			if (spec != null) {
				query.orderBy(spec);
			}
		}
	}

	private static OrderSpecifier<?> orderByAsc(QCustomerAddress ca, QCustomer c, QCustomerCategory cc,
			String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {
			case "id":
				spec = ca.id.asc();
				break;

			case "customerId":
				spec = c.id.asc();
				break;

			case "customerCategoryName":
				spec = cc.name.asc();
				break;

			case "customerWechatName":
				spec = c.wechatName.asc();
				break;

			case "customerName":
				spec = c.name.asc();
				break;

			case "category":
				spec = ca.category.asc();
				break;

			case "linkman":
				spec = ca.linkman.asc();
				break;

			case "telephone":
				spec = ca.telephone.asc();
				break;

			case "address":
				spec = ca.address.asc();
				break;

		}
		return spec;
	}

	private static OrderSpecifier<?> orderByDesc(QCustomerAddress ca, QCustomer c, QCustomerCategory cc,
			String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {
			case "id":
				spec = ca.id.desc();
				break;

			case "customerId":
				spec = c.id.desc();
				break;

			case "customerCategoryName":
				spec = cc.name.desc();
				break;

			case "customerWechatName":
				spec = c.wechatName.desc();
				break;

			case "customerName":
				spec = c.name.desc();
				break;

			case "category":
				spec = ca.category.desc();
				break;

			case "linkman":
				spec = ca.linkman.desc();
				break;

			case "telephone":
				spec = ca.telephone.desc();
				break;

			case "address":
				spec = ca.address.desc();
				break;
		}
		return spec;
	}
}