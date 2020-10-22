package com.easysoft.sma.domain.dto;

import java.math.BigDecimal;

import com.easysoft.lib.jdb.domain.dto.DetailResponse;
import com.easysoft.sma.domain.entity.Product;

public class ProductDetailResponse extends DetailResponse {

    private String categoryName;

    private int salesYear;

    private String name;

    private BigDecimal price;

    private String packUnit;

    private BigDecimal spec;

    private String specUnit;

    private String supportDeliveryMode;

    private String status;

    private String remark;

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

    public ProductDetailResponse() {
    }

    public ProductDetailResponse(Product entity, String categoryName) {
        super(entity.getId(), entity.getCreater(), entity.getCreateTime(), entity.getUpdater(), entity.getUpdateTime());
        this.categoryName = categoryName;
        this.salesYear = entity.getSalesYear();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.packUnit = entity.getPackUnit();
        this.spec = entity.getSpec();
        this.specUnit = entity.getSpecUnit();
        this.status = entity.getStatus();
        this.remark = entity.getRemark();
        this.supportDeliveryMode = entity.getSupportDeliveryMode();
    }
}