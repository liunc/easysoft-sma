package com.easysoft.sma.ui.model.ktdatatable;

public class KTDatatableMeta {

	private int page;

	private int pages;

	private int perpage;

	private long total;

	private String sort;

	private String field;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPerpage() {
		return perpage;
	}

	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setRequest(int page, int perpage, KTDatatableSort sort) {

		this.page = page;
		this.perpage = perpage;
		this.sort = sort.getSort();
		this.field = sort.getField();
	}

	public void setResponse(long total) {

		this.total = total;
		this.pages = (int) (total / this.perpage);
		if (this.total % this.perpage > 0) {
			this.pages += 1;
		}
	}
}
