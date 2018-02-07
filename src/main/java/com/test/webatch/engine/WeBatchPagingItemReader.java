package com.test.webatch.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;

import com.test.webatch.dao.BasicDao;
import com.test.webatch.dao.Page;

/**
 * 说明：spring batch 中只提供了IbatisPagingItemReader，这里只有重写了
 * 以SqlSession替换SqlMapClient，SqlSessionTemplate替换SqlMapClientTemplate
 * */

public class WeBatchPagingItemReader<T> extends AbstractPagingItemReader<T> {
	public String objFullName;
	@Autowired
	private BasicDao<T> basicDao;
	private Map<String, Object> parameterValues;

	public WeBatchPagingItemReader() {
		setName(ClassUtils.getShortName(WeBatchPagingItemReader.class));
	}

	/**
	 * Check mandatory properties.
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		// Assert.notNull(sqlSession);
		// sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		// Assert.notNull(queryId);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doReadPage() {
		Page<T> page = new Page<T>();
		page.setPageNumber(getPage());
		page.setPageSize(getPageSize());

		basicDao.queryForPage(objFullName, parameterValues, page);

		Map<String, Object> parameters = new HashMap<String, Object>();
		if (parameterValues != null) {
			parameters.putAll(parameterValues);
		}
		parameters.put("_page", getPage());
		parameters.put("_pagesize", getPageSize());
		parameters.put("_skiprows", getPage() * getPageSize());
		if (results == null) {
			results = new ArrayList<T>();// CopyOnWriteArrayList
		} else {
			results.clear();
		}
		results.addAll(page.getRows());
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
	}

	public void setObjFullName(String objFullName) {
		this.objFullName = objFullName;
	}

	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}

}
