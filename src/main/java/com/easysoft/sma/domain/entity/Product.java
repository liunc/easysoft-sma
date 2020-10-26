package com.easysoft.sma.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.easysoft.lib.jdb.domain.valueobject.ZeroOne;

@Entity
@Table(name = "product")
public class Product extends BaseEntity {

	private static final long serialVersionUID = -8116240182698898840L;

	@Column(name = "category_id", columnDefinition = "CHAR(32) COMMENT '分类ID'", nullable = false)
	private String categoryId;

	@Column(name = "salesYear", columnDefinition = "INT COMMENT '销售年份'", nullable = false)
	private int salesYear;

	@Column(name = "name", columnDefinition = "VARCHAR(40) COMMENT '名称'", nullable = false)
	private String name;

	@Column(name = "price", columnDefinition = "DECIMAL(10,2) COMMENT '单价'", nullable = false)
	private BigDecimal price;

	@Column(name = "pack_unit", columnDefinition = "VARCHAR(10) COMMENT '包装单位'", nullable = false)
	private String packUnit;

	@Column(name = "spec", columnDefinition = "DECIMAL(10,2) COMMENT '规格'", nullable = false)
	private BigDecimal spec;

	@Column(name = "spec_unit", columnDefinition = "VARCHAR(10) COMMENT '规格单位'", nullable = false)
	private String specUnit;

	@Column(name = "support_delivery_mode", columnDefinition = "VARCHAR(20) COMMENT '支持的交付方式， 1快递，2上门，3自取，支持一种以上，用“,”分隔'", nullable = false)
	private String supportDeliveryMode;

	@Column(columnDefinition = "CHAR(1) COMMENT '状态 1有效 0无效'", nullable = false)
	private String status;

	@Column(name = "remark", columnDefinition = "VARCHAR(512) COMMENT '备注'", nullable = false)
	private String remark;

	public String getCategoryId() {
		return categoryId;
	}

	public int getSalesYear() {
		return salesYear;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getPackUnit() {
		return packUnit;
	}

	public BigDecimal getSpec() {
		return spec;
	}

	public String getSpecUnit() {
		return specUnit;
	}

	public String getSupportDeliveryMode() {
		return supportDeliveryMode;
	}

	public String getStatus() {
		return status;
	}

	public String getRemark() {
		return remark;
	}

	public void create(String categoryId, String categoryName, int salesYear, BigDecimal price, String packUnit,
			BigDecimal spec, String specUnit, String supportDeliveryMode, String remark) {

		super.create();
		this.categoryId = categoryId;
		this.salesYear = salesYear;
		this.price = price;
		this.packUnit = packUnit;
		this.spec = spec;
		this.specUnit = specUnit;
		this.supportDeliveryMode = supportDeliveryMode;
		this.status = ZeroOne.ONE;
		this.name = String.format("%s(%s%s)", categoryName, this.spec, this.specUnit);
		this.remark = remark;
	}

	public void update(BigDecimal price, String packUnit, BigDecimal spec, String specUnit, String supportDeliveryMode,
			String remark) {

		this.price = price;
		this.packUnit = packUnit;
		this.spec = spec;
		this.specUnit = specUnit;
		this.supportDeliveryMode = supportDeliveryMode;
		this.remark = remark;
	}

	public void changeStatus() {
		if (ZeroOne.ONE.equals(this.status)) {
			this.status = ZeroOne.ZERO;
			return;
		}
		this.status = ZeroOne.ONE;
	}

}