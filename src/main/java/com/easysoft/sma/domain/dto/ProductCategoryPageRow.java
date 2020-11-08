package com.easysoft.sma.domain.dto;

import org.springframework.data.domain.Sort;

import com.easysoft.sma.domain.entity.QProductCategory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

public class ProductCategoryPageRow {

	private String id;

	private String name;

	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static void setOrder(JPAQuery<ProductCategoryPageRow> query, QProductCategory qcc, Sort sort) {

		for (Sort.Order o : sort) {

			String property = o.getProperty();
			OrderSpecifier<?> spec = o.isAscending() ? orderByAsc(qcc, property) : orderByDesc(qcc, property);

			if (spec != null) {
				query.orderBy(spec);
			}
		}

	}

	private static OrderSpecifier<?> orderByAsc(QProductCategory qcc, String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {

			case "name":
				spec = qcc.name.asc();
				break;

			case "status":
				spec = qcc.name.asc();
				break;

			case "remark":
				spec = qcc.remark.asc();
				break;
		}
		return spec;
	}

	private static OrderSpecifier<?> orderByDesc(QProductCategory qcc, String property) {
		OrderSpecifier<?> spec = null;
		switch (property) {

			case "name":
				spec = qcc.name.desc();
				break;

			case "status":
				spec = qcc.name.desc();
				break;

			case "remark":
				spec = qcc.remark.desc();
				break;
		}
		return spec;
	}

}
