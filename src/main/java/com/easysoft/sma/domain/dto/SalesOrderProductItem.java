package com.easysoft.sma.domain.dto;

import java.math.BigDecimal;

public class SalesOrderProductItem {

    private String id;

    private String categoryName;

    private int salesYear;

    private String name;

    private BigDecimal price;

    private BigDecimal actualPrice;

    private String packUnit;

    private BigDecimal spec;

    private String specUnit;

    private String supportDeliveryMode;

    private BigDecimal quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSalesYear() {
        return salesYear;
    }

    public void setSalesYear(int salesYear) {
        this.salesYear = salesYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

    public String getPackUnit() {
        return packUnit;
    }

    public void setPackUnit(String packUnit) {
        this.packUnit = packUnit;
    }

    public BigDecimal getSpec() {
        return spec;
    }

    public void setSpec(BigDecimal spec) {
        this.spec = spec;
    }

    public String getSpecUnit() {
        return specUnit;
    }

    public void setSpecUnit(String specUnit) {
        this.specUnit = specUnit;
    }

    public String getSupportDeliveryMode() {
        return supportDeliveryMode;
    }

    public void setSupportDeliveryMode(String supportDeliveryMode) {
        this.supportDeliveryMode = supportDeliveryMode;
    } 

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}