package com.easysoft.sma.ui.model;

public class BootstrapTableRequest {
    private int limit;

    private int offset;

    private String sort;

    private String order;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPage() {
        return this.offset / this.limit;
    }

    public boolean isDesc() {
        return this.order.equalsIgnoreCase("desc");
    }
}