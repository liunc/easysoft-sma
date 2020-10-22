package com.easysoft.sma.domain.dto;

import java.math.BigDecimal;

import com.easysoft.sma.domain.entity.QProduct;
import com.easysoft.sma.domain.entity.QProductCategory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.data.domain.Sort;

public class ProductPageResponse {
    private String id;

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

    public static void setOrder(JPAQuery<ProductPageResponse> query, QProduct p, QProductCategory pc, Sort sort) {

        for (Sort.Order o : sort) {

            String property = o.getProperty();
            OrderSpecifier<?> spec = o.isAscending() ? orderByAsc(p, pc, property) : orderByDesc(p, pc, property);

            if (spec != null) {
                query.orderBy(spec);
            }
        }
    }

    private static OrderSpecifier<?> orderByAsc(QProduct p, QProductCategory pc, String property) {
        OrderSpecifier<?> spec = null;
        switch (property) {
            case "id":
                spec = p.id.asc();
                break;

            case "categoryName":
                spec = pc.name.asc();
                break;

            case "salesYear":
                spec = p.salesYear.asc();
                break;

            case "name":
                spec = p.name.asc();
                break;

            case "price":
                spec = p.price.asc();
                break;

            case "packUnit":
                spec = p.packUnit.asc();
                break;

            case "spec":
                spec = p.spec.asc();
                break;

            case "specUnit":
                spec = p.specUnit.asc();
                break;

            case "supportDeliveryMode":
                spec = p.supportDeliveryMode.asc();
                break;

            case "status":
                spec = p.status.asc();
                break;

            case "remark":
                spec = p.remark.asc();
                break;
        }
        return spec;
    }

    private static OrderSpecifier<?> orderByDesc(QProduct p, QProductCategory pc, String property) {
        OrderSpecifier<?> spec = null;
        switch (property) {
            case "id":
                spec = p.id.desc();
                break;

            case "categoryName":
                spec = pc.name.desc();
                break;

            case "salesYear":
                spec = p.salesYear.desc();
                break;

            case "name":
                spec = p.name.desc();
                break;

            case "price":
                spec = p.price.desc();
                break;

            case "packUnit":
                spec = p.packUnit.desc();
                break;

            case "spec":
                spec = p.spec.desc();
                break;

            case "specUnit":
                spec = p.specUnit.desc();
                break;

            case "supportDeliveryMode":
                spec = p.supportDeliveryMode.desc();
                break;

            case "status":
                spec = p.status.desc();
                break;

            case "remark":
                spec = p.remark.desc();
                break;
        }
        return spec;
    }
}