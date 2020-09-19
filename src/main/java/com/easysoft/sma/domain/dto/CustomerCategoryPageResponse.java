package com.easysoft.sma.domain.dto;

import org.springframework.data.domain.Sort;

import com.easysoft.sma.domain.entity.QCustomerCategory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

public class CustomerCategoryPageResponse {

	private String id;

	private String name;

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

	public static void setOrder(JPAQuery<CustomerCategoryPageResponse> query, QCustomerCategory cc, Sort sort) {

		for (Sort.Order o : sort) {

			String property = o.getProperty();
			OrderSpecifier<?> spec = o.isAscending() ? orderByAsc(cc, property) : orderByDesc(cc, property);

			if (spec != null) {
				query.orderBy(spec);
			}
		}

	}

	private static OrderSpecifier<?> orderByAsc(QCustomerCategory cc, String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {

		case "name":
			spec = cc.name.asc();
			break;

		case "remark":
			spec = cc.remark.asc();
			break;
		}
		return spec;
	}

	private static OrderSpecifier<?> orderByDesc(QCustomerCategory cc, String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {

		case "name":
			spec = cc.name.desc();
			break;

		case "remark":
			spec = cc.remark.desc();
			break;
		}
		return spec;
	}

}
