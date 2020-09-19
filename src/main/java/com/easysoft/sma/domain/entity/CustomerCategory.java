package com.easysoft.sma.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.easysoft.lib.jdb.domain.valueobject.ZeroOne;

/*
 * 客户分类实体类
 * 
 * @author 刘年超
 */
@Entity
@Table(name = "customer_category")
public class CustomerCategory extends BaseEntity {

	private static final long serialVersionUID = -8116240182698898840L;

	@Column(columnDefinition = "VARCHAR(40) COMMENT '名称'", nullable = false)
	private String name;

	@Column(columnDefinition = "CHAR(1) COMMENT '状态 1有效 0无效'", nullable = false)
	private String status;

	@Column(columnDefinition = "VARCHAR(512) COMMENT '备注'")
	private String remark;

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public String getRemark() {
		return remark;
	}

	public void create(String name, String remark) {

		super.create();
		this.name = name;
		this.remark = remark;
		this.status = ZeroOne.ONE;
	}

	public void update(String name, String remark) {

		this.name = name;
		this.remark = remark;
	}

	public void change() {
		if (ZeroOne.ONE.equals(this.status)) {
			this.status = ZeroOne.ZERO;
			return;
		}
		this.status = ZeroOne.ONE;
	}
}
