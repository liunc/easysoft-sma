package com.easysoft.sma.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sales_order_product")
public class SalesOrderProduct extends BaseEntity {

	private static final long serialVersionUID = -8116240182698898840L;
 
	@Column(name = "order_id", columnDefinition = "CHAR(32) COMMENT '订单ID'", nullable = false)
	private String orderId;

	@Column(name = "product_id", columnDefinition = "CHAR(32) COMMENT '产品ID'", nullable = false)
	private String productId;

	@Column(name = "category_id", columnDefinition = "CHAR(32) COMMENT '产品分类ID，冗余字段'", nullable = false)
	private String categoryId;

	@Column(name = "salesYear", columnDefinition = "INT COMMENT '销售年份，冗余字段'", nullable = false)
	private int salesYear;

	@Column(name = "name", columnDefinition = "VARCHAR(40) COMMENT '产品名称，冗余字段'", nullable = false)
	private String name;

	@Column(name = "price", columnDefinition = "DECIMAL(10,2) COMMENT '单价，冗余字段'", nullable = false)
	private BigDecimal price;
	
	@Column(name = "actual_price", columnDefinition = "DECIMAL(10,2) COMMENT '实际成交的单价'")
	private BigDecimal actualPrice;

	@Column(name = "pack_unit", columnDefinition = "VARCHAR(10) COMMENT '包装单位，冗余字段'", nullable = false)
	private String packUnit;

	@Column(name = "spec", columnDefinition = "DECIMAL(10,2) COMMENT '规格，冗余字段'", nullable = false)
	private BigDecimal spec;

	@Column(name = "spec_unit", columnDefinition = "VARCHAR(10) COMMENT '规格单位，冗余字段'", nullable = false)
	private String specUnit;

	@Column(name = "quantity", columnDefinition = "DECIMAL(10,2) COMMENT '数量'", nullable = false)
	private BigDecimal quantity;

	public String getOrderId() {
		return orderId;
	}

	public String getProductId() {
		return productId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public int getSalesYear() {
		return salesYear;
	}

	public String getName() {
		return name;
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

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void create(Product product, String orderId, BigDecimal actualPrice, BigDecimal quantity) {
		super.create();
		this.productId = product.getId();
		this.categoryId = product.getCategoryId();
		this.salesYear = product.getSalesYear();
		this.name = product.getName();
		this.packUnit = product.getPackUnit();
		this.spec = product.getSpec();
		this.specUnit = product.getSpecUnit();
		this.price = product.getPrice();
		this.actualPrice = actualPrice;
		this.orderId = orderId;
		this.quantity = quantity;
	}
}
