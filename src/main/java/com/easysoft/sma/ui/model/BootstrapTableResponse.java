package com.easysoft.sma.ui.model;

import java.util.ArrayList;
import java.util.List;

public class BootstrapTableResponse<T> {

    private long total;

    private List<T> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        if (this.rows == null) {
            this.rows = new ArrayList<T>();
        }
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}