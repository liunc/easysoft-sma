package com.easysoft.sma.ui.model.ktdatatable;

public class KTDatatableSort {

	private String field;
	
	private String sort;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public boolean isDesc(){
		return this.sort.equalsIgnoreCase("desc");
	}
}
