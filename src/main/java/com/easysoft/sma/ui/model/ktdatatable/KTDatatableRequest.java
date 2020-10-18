package com.easysoft.sma.ui.model.ktdatatable;

import javax.servlet.http.HttpServletRequest;

public class KTDatatableRequest<T> {

    private KTDatatablePagination pagination;

    private KTDatatableSort sort;

    private T query;

    public KTDatatablePagination getPagination() {
        return pagination;
    }

    public void setPagination(KTDatatablePagination pagination) {
        this.pagination = pagination;
    }

    public KTDatatableSort getSort() {
        return sort;
    }

    public void setSort(KTDatatableSort sort) {
        this.sort = sort;
    }

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }

    public KTDatatableRequest() {

        this.pagination = new KTDatatablePagination();
        this.sort = new KTDatatableSort();
    }

    public KTDatatableRequest(HttpServletRequest request) {
        this();

        this.pagination.setPage(Integer.valueOf(request.getParameter("pagination[page]")) - 1);
        this.pagination.setPerpage(Integer.valueOf(request.getParameter("pagination[perpage]")));
        this.sort.setField(request.getParameter("sort[field]"));
        this.sort.setSort(request.getParameter("sort[sort]"));

    }
}