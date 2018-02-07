package com.test.webatch.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @param <T>
 */
public class Page<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String _SORT_NAME = "_SORT_NAME";

	public static final String _SORT_ORDER = "_SORT_ORDER";
	
	private Query query = new Query();

	private int pageNumber = 1;

	private int pageSize = 10;

	private String sortName = "1";

    private String sortOrder="asc";
    
    private List<Map<String,String>> _SORT_LIST = new ArrayList<Map<String,String>>();
    
    private long total = 0;
    
    private List<T> rows = new ArrayList<T>();
    
    public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}


	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<Map<String, String>> get_SORT_LIST() {
		return _SORT_LIST;
	}

	public void set_SORT_LIST(List<Map<String, String>> _SORT_LIST) {
		this._SORT_LIST = _SORT_LIST;
	}

}
