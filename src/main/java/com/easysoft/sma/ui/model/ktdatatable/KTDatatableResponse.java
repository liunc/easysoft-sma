package com.easysoft.sma.ui.model.ktdatatable;

import java.util.List;

public class KTDatatableResponse<T> {
    
    private KTDatatableMeta meta;

    private List<T> data;

    public KTDatatableMeta getMeta() {
        return meta;
    }

    public void setMeta(KTDatatableMeta meta) {
        this.meta = meta;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    
}